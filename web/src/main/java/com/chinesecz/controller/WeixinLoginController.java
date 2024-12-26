package com.chinesecz.controller;

import com.chinesecz.common.constants.Constants;
import com.chinesecz.common.response.Response;
import com.chinesecz.service.weixin.IWeixinLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/login/")
public class WeixinLoginController {

    @Resource
    private IWeixinLoginService weixinLoginService;

    /**
     * http://youcury.natapp1.cc/api/v1/login/weixin_qrcode_ticket
     * @return
     */
    @RequestMapping("weixin_qrcode_ticket")
    public Response<String> weixinQrCodeTicket() {
        try {
            log.info("开始获取二维码所需ticket");
            String qrCodeTicket = weixinLoginService.createQrCodeTicket();
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(qrCodeTicket)
                    .build();

        } catch (Exception e) {
            log.error("获取二维码ticket失败");
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }

    }

    @RequestMapping("check_login")
    public Response<String> checkLogin(@RequestParam("ticket") String ticket) {
        try {
            log.info("校验登录状态");
            String openid = weixinLoginService.checkLogin(ticket);
            log.info("openid:{}",openid);
            if (openid != null) {
                return Response.<String>builder()
                        .code(Constants.ResponseCode.SUCCESS.getCode())
                        .info(Constants.ResponseCode.SUCCESS.getInfo())
                        .data(openid)
                        .build();
            }
            return Response.<String>builder()
                    .code(Constants.ResponseCode.NO_LOGIN.getCode())
                    .info(Constants.ResponseCode.NO_LOGIN.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("查询登录状态失败");
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }



}
