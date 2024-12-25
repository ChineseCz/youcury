package com.chinesecz.service.impl;


import com.chinesecz.service.IWeixinLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeixinLoginServiceImpl implements IWeixinLoginService {

    @Value("${weixin.config.app-id}")
    private String appid;
    @Value("${weixin.config.app-secret")

    @Override
    public String createQrCodeTicket() throws Exception {
        return "";
    }

    @Override
    public String checkLogin(String ticket) {
        return "";
    }

    @Override
    public void saveLoginState(String ticket, String openid) throws Exception {

    }
}
