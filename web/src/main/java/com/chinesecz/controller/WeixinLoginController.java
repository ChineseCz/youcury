package com.chinesecz.controller;

import com.chinesecz.common.constants.Constants;
import com.chinesecz.common.response.Response;
import com.chinesecz.service.IWeixinLoginService;
import com.chinesecz.service.weixin.IWeixinApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
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


}