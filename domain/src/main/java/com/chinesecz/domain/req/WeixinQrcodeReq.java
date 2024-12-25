package com.chinesecz.domain.req;


import lombok.*;

/**

 获取 Access Token 文档：
    https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
 生成带参数的二维码：
    https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html

 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeixinQrcodeReq {
    private int expire_seconds;
    private String action_name;
    private ActionInfo action_info;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActionInfo {
        Scene scne;

        public static class Scene {
            int scene_id;
            String scene_str;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ActionNameTypeVO {
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private String code;
        private String info;
    }
}
