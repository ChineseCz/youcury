package com.chinesecz.service;

import com.chinesecz.domain.req.ShopCartReq;
import com.chinesecz.domain.res.PayOrderRes;

public interface IOrderService {
    PayOrderRes createOrder(ShopCartReq shopCartReq) throws Exception;
}
