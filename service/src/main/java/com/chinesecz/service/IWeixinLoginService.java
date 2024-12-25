package com.chinesecz.service;

/**
 * 微信登录服务
 */
public interface IWeixinLoginService {
    String createQrCodeTicket() throws Exception;

    String checkLogin(String ticket);

    void saveLoginState(String ticket,String openid) throws Exception;
}
