package com.chinesecz.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Constants {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ResponseCode {
        SUCCESS("00", "调用成功"),
        UN_ERROR("01", "调用失败"),
        ILLEGAL_PARAMETER("02", "非法参数"),
        NO_LOGIN("03", "未登录"),
        ;

        private String code;
        private String info;


    }




}
