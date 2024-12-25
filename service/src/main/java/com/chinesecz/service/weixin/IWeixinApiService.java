package com.chinesecz.service.weixin;

import com.chinesecz.domain.req.WeixinQrcodeReq;
import com.chinesecz.domain.res.WeixinQrcodeRes;
import com.chinesecz.domain.res.WeixinTokenRes;
import com.chinesecz.domain.vo.WeixinTemplateMessageVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 调用微信Api
 */
public interface IWeixinApiService {
    /**
     * 接口文档：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     * @param grant_type
     * @param appid
     * @param secret
     * @return
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenRes> getToken(@Query("grant_type") String grant_type,
                                  @Query("appid") String appid,
                                  @Query("secret") String secret);

    /**
     * 接口文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
     * @param access_token
     * @param req
     * @return
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrcodeRes> createQrCode(@Query("access_token") String access_token,
                                       @Body WeixinQrcodeReq req);

    /**
     * 接口文档：https://mp.weixin.qq.com/debug/cgi-bin/readtmpl?t=tmplmsg/faq_tmpl
     * @param accessToken
     * @param weixinTemplateMessageVO
     * @return
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(@Query("access_token") String accessToken, @Body WeixinTemplateMessageVO weixinTemplateMessageVO);
}
