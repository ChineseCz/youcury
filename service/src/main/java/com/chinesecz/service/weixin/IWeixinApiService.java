package com.chinesecz.service.weixin;

import com.chinesecz.domain.req.WeixinQrcodeReq;
import com.chinesecz.domain.res.WeixinQrcodeRes;
import com.chinesecz.domain.res.WeixinTokenRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Call<WeixinQrcodeRes> createQrCode(@Query("access_token") String access_token,
                                       @Body WeixinQrcodeReq req);
}
