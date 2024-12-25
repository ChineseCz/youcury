package com.chinesecz.domain.res;

import lombok.Data;

@Data
public class WeixinQrcodeRes {
    private String ticket;
    private Long expire_seconds;
    private String url;
}
