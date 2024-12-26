package com.chinesecz.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Constants {
    @AllArgsConstructor
    @Getter
    public enum ResponseCode {
        SUCCESS("00", "调用成功"),
        UN_ERROR("01", "调用失败"),
        ILLEGAL_PARAMETER("02", "非法参数"),
        NO_LOGIN("03", "未登录"),
        ;

        private final String code;
        private final String info;


    }
    @Getter
    @AllArgsConstructor
    public enum OrderStatusEnum {
        CREATE("CREATE","订单创建"),
        PAY_WAIT("PAY_WAIT","等待支付"),
        PAY_SUCCESS("PAY_SUCCESS","支付完成"),
        FINISH("FNISH","交易完成"),
        CLOASE("CLOSE","超市关单"),
        ;


        private final String code;
        private final String info;
    }


}
