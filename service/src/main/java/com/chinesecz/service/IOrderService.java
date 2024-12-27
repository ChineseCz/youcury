package com.chinesecz.service;

import com.chinesecz.domain.req.ShopCartReq;
import com.chinesecz.domain.res.PayOrderRes;

import java.util.List;

public interface IOrderService {
    PayOrderRes createOrder(ShopCartReq shopCartReq) throws Exception;

    void changeOrderPaySuccess(String orderId);

    boolean changeOrderClose(String orderId);

    List<String> queryNoPayNotifyOrder();

    List<String> queryTimeoutCloseOrderList();


}
