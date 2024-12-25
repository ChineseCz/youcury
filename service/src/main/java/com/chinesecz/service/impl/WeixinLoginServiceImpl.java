package com.chinesecz.service.impl;


import com.chinesecz.domain.req.WeixinQrcodeReq;
import com.chinesecz.domain.res.WeixinQrcodeRes;
import com.chinesecz.domain.res.WeixinTokenRes;
import com.chinesecz.service.IWeixinLoginService;
import com.chinesecz.service.weixin.IWeixinApiService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;

@Service
public class WeixinLoginServiceImpl implements IWeixinLoginService {

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
        String accessToken = weixinAccessToken.getIfPresent(appid);
        if (accessToken == null) {
            Call<WeixinTokenRes> call= weixinApiService.getToken("client_credential", appid, appSecret);
            WeixinTokenRes weixinTokenRes = call.execute().body();
            accessToken = weixinTokenRes.getAccess_token();
            weixinAccessToken.put(appid, accessToken);
        }

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

    }
}
