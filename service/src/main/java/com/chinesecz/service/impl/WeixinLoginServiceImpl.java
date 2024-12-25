package com.chinesecz.service.impl;


import com.chinesecz.service.IWeixinLoginService;
import com.chinesecz.service.weixin.IWeixinApiService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WeixinLoginServiceImpl implements IWeixinLoginService {

    @Value("${weixin.config.app-id}")
    private String appid;
    @Value("${weixin.config.app-secret")
    private String appSecret;
    @Value("${weixin.config.template_id}")

    @Resource
    private Cache<String, String> weixinAccessToken;

    @Resource
    private IWeixinApiService weixinApiService;

    @Resource
    private Cache<String, String> openidToken;

    private String templateId;
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
