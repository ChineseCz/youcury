package com.chinesecz.controller;

import com.chinesecz.common.weixin.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.security.Signature;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {
    @Value("weixin.config.originalid")
    private String originalid;

    @Value("${weixin.config.token}")
    private String token;

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
}
