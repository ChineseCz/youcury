package com.chinesecz.service.impl;


import com.chinesecz.domain.req.WeixinQrcodeReq;
import com.chinesecz.domain.req.WeixinTemplateMessageReq;
import com.chinesecz.domain.res.WeixinQrcodeRes;
import com.chinesecz.domain.res.WeixinTemplateMessageRes;
import com.chinesecz.domain.res.WeixinTokenRes;
import com.chinesecz.service.IWeixinLoginService;
import com.chinesecz.service.weixin.IWeixinApiService;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinLoginServiceImpl implements IWeixinLoginService {

    private static final Logger log = LoggerFactory.getLogger(WeixinLoginServiceImpl.class);
    @Value("${weixin.config.app-id}")
    private String appid;
    @Value("${weixin.config.app-secret}")
    private String appSecret;
    @Value("${weixin.config.template_id}")
    private String templateId;

    @Resource
    private Cache<String, String> weixinAccessToken;

    @Resource
    private IWeixinApiService weixinApiService;

    @Resource
    private Cache<String, String> openidToken;


    @Override
    public String createQrCodeTicket() throws Exception {
        // 先获取accessToken
        String accessToken = getWeixinAccessToken();

        //生成ticket
        WeixinQrcodeReq weixinQrcodeReq = WeixinQrcodeReq.builder()
                .expire_seconds(2592000)
                .action_name(WeixinQrcodeReq.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrcodeReq.ActionInfo.builder()
                        .scene(WeixinQrcodeReq.ActionInfo.Scene.builder()
                                .scene_id(100601)
                                .build())
                        .build())
                .build();

        Call<WeixinQrcodeRes> call = weixinApiService.createQrCode(accessToken,weixinQrcodeReq);
        WeixinQrcodeRes weixinQrcodeRes = call.execute().body();


        return weixinQrcodeRes.getTicket();


    }

    @Override
    public String checkLogin(String ticket) {
        return openidToken.getIfPresent(ticket);
    }

    @Override
    public void saveLoginState(String ticket, String openid) throws Exception {
        openidToken.put(ticket,openid);

        String accessToken = getWeixinAccessToken();

        //发送模板消息
        Map<String, Map<String, String>> data = new HashMap<>();
        WeixinTemplateMessageReq.put(data, WeixinTemplateMessageReq.TemplateKey.USER, openid);

        WeixinTemplateMessageReq templateMessageDTO = new WeixinTemplateMessageReq(openid, templateId);
        templateMessageDTO.setUrl("https://github.com");
        templateMessageDTO.setData(data);

        log.info("模板信息");
        Call<WeixinTemplateMessageRes> call = weixinApiService.sendMessage(accessToken, templateMessageDTO);
        WeixinTemplateMessageRes weixinTemplateMessageRes = call.execute().body();
        log.info("模板响应{}",weixinTemplateMessageRes);


    }

    public String getWeixinAccessToken() throws IOException {
        String accessToken = weixinAccessToken.getIfPresent(appid);
        if (null == accessToken) {
            Call<WeixinTokenRes> call = weixinApiService.getToken("client_credential", appid, appSecret);
            WeixinTokenRes weixinTokenRes = call.execute().body();
            assert weixinTokenRes != null;
            accessToken = weixinTokenRes.getAccess_token();
            weixinAccessToken.put(appid, accessToken);
        }
        return accessToken;
    }
}
