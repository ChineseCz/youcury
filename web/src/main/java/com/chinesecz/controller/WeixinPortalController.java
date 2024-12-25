package com.chinesecz.controller;

import com.chinesecz.common.weixin.MessageEntity.MessageEntity;
import com.chinesecz.common.weixin.MessageEntity.MessagePictureEntity;
import com.chinesecz.common.weixin.MessageEntity.MessageTextEntity;
import com.chinesecz.common.weixin.SignatureUtil;
import com.chinesecz.common.weixin.XmlUtil;
import com.chinesecz.service.impl.WeixinLoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {

    private final WeixinLoginServiceImpl weixinLoginServiceImpl;
    @Value("${weixin.config.originalid}")
    private String originalid;
    @Value("${weixin.config.token}")
    private String token;

    public WeixinPortalController(WeixinLoginServiceImpl weixinLoginServiceImpl) {
        this.weixinLoginServiceImpl = weixinLoginServiceImpl;
    }

    @GetMapping(value = "receive")
    public String validate(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr
                           ) {
        try {
            log.info("验签开始 [{},{},{},{}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("参数有空，无效");
            }
            if (!SignatureUtil.check(token, signature, timestamp, nonce)) {
                return null;
            }
            else
                return echostr;

        } catch (Exception e) {
            log.error("验签失败");
            return null;
        }

    }

    /**
     * POST请求接口，接收公众号发送的任意类型信息并做相应处理。
     * @param requestBody
     * @return
     */
    @PostMapping(value = "receive",produces = "application/xml; charset=UTF-8")
    public String receiveMsg(@RequestBody String requestBody) {
        try {
            log.info("接受信息");
            MessageEntity msg = XmlUtil.xmlToBean(requestBody, MessageEntity.class);

            //扫码事件
            if (msg.getMsgType().equals("event") && msg.getEvent().equals("SCAN")) {
                weixinLoginServiceImpl.saveLoginState(msg.getTicket(), msg.getFromUserName());
                return buildMessageTextEntity(msg.getFromUserName(),"登录成功");
            }


            return buildMessageTextEntity(msg.getFromUserName(),msg.getContent());

        } catch (Exception e) {
            log.error("失败",e);
            return "";
        }
    }

    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }
}
