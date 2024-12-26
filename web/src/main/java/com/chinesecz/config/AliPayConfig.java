package com.chinesecz.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliPayConfigProperties.class)
public class AliPayConfig {

    @Bean("alipayCilent")
    @ConditionalOnProperty(value = "alipay.enabled",havingValue = "true")
    public AlipayClient alipayClient(AliPayConfigProperties aliPayConfigProperties) {
        return new DefaultAlipayClient(aliPayConfigProperties.getGatewayUrl(),
                aliPayConfigProperties.getApp_id(),
                aliPayConfigProperties.getMerchant_private_key(),
                aliPayConfigProperties.getFormat(),
                aliPayConfigProperties.getCharset(),
                aliPayConfigProperties.getAlipay_public_key(),
                aliPayConfigProperties.getSign_type()
        );
    }
}
