package com.chinesecz.service.impl;

import com.chinesecz.common.constants.Constants;
import com.chinesecz.dao.IOrderDao;
import com.chinesecz.domain.po.PayOrder;
import com.chinesecz.domain.req.ShopCartReq;
import com.chinesecz.domain.res.PayOrderRes;
import com.chinesecz.domain.vo.ProductVO;
import com.chinesecz.service.IOrderService;
import com.chinesecz.service.rpc.ProductRPC;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private IOrderDao orderDao;
    @Resource
    private ProductRPC productRPC;

    @Override
    public PayOrderRes createOrder(ShopCartReq shopCartReq) throws Exception {
        // 查询 用户是否存在 未支付订单或者掉单
        PayOrder payOrderReq = new PayOrder();
        payOrderReq.setProductId(shopCartReq.getProductId());
        payOrderReq.setUserId(shopCartReq.getUserId());

        PayOrder unpaidOrder =  orderDao.queryUnPayOrder(payOrderReq);

        //支付单未支付
        if (null != unpaidOrder && Constants.OrderStatusEnum.PAY_WAIT.getCode().equals(unpaidOrder.getStatus())) {
            return PayOrderRes.builder()
                    .orderId(unpaidOrder.getOrderId())
                    .userId(unpaidOrder.getUserId())
                    .payUrl(unpaidOrder.getPayUrl())
                    .build();
        }
        //支付链接生成失败，掉单
        else if (null != unpaidOrder) {

        }


        //首次下单——查询商品，创建订单
        ProductVO productVO = productRPC.queryProductByProductId(shopCartReq.getProductId());
        String orderId = RandomStringUtils.randomNumeric(16);
        orderDao.insert(PayOrder.builder()
                        .userId(shopCartReq.getUserId())
                        .productId(shopCartReq.getProductId())
                        .productName(productVO.getProductName())
                        .orderTime(new Date())
                        .orderId(orderId)
                        .totalAmount(productVO.getPrice())
                        .status(Constants.OrderStatusEnum.CREATE.getCode())
                .build());

        //后续支付单



        return PayOrderRes.builder()
                .orderId(orderId)
                .userId(shopCartReq.getUserId())
                .payUrl(null)
                .build();
    }
}
