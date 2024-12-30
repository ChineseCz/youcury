package com.chinesecz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.chinesecz.common.constants.Constants;
import com.chinesecz.dao.IOrderDao;
import com.chinesecz.domain.po.PayOrder;
import com.chinesecz.domain.req.ShopCartReq;
import com.chinesecz.domain.res.PayOrderRes;
import com.chinesecz.domain.vo.ProductVO;
import com.chinesecz.service.IOrderService;
import com.chinesecz.service.redis.IRedisService;
import com.chinesecz.service.rpc.ProductRPC;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Value("${alipay.return_url}")
    private String returnUrl;

    @Resource
    private AlipayClient alipayClient;
    @Resource
    private IOrderDao orderDao;
    @Resource
    private ProductRPC productRPC;

    @Resource
    private EventBus eventBus;

    @Resource
    private IRedisService iRedisService;

    /**
     * 创建订单
     * @param shopCartReq 购物请求
     * @return
     * @throws Exception
     */
    @Override
    public PayOrderRes createOrder(ShopCartReq shopCartReq) throws Exception {
        // 查询 用户是否存在 未支付订单或者掉单
        PayOrder payOrderReq = new PayOrder();
        payOrderReq.setProductId(shopCartReq.getProductId());
        payOrderReq.setUserId(shopCartReq.getUserId());

        PayOrder unpaidOrder =  orderDao.queryUnPayOrder(payOrderReq);

        //支付单未支付
        if (null != unpaidOrder && Constants.OrderStatusEnum.PAY_WAIT.getCode().equals(unpaidOrder.getStatus())) {
            log.info("订单创建过，还未支付");
            return PayOrderRes.builder()
                    .orderId(unpaidOrder.getOrderId())
                    .userId(unpaidOrder.getUserId())
                    .payUrl(unpaidOrder.getPayUrl())
                    .build();
        }
        //支付链接生成失败，掉单
        else if (null != unpaidOrder && Constants.OrderStatusEnum.CREATE.getCode().equals(unpaidOrder.getStatus()) ) {
            log.info("订单创建过，但掉单");
            PayOrder payOrder = createPayOrder(unpaidOrder.getProductId(),
                    unpaidOrder.getOrderId(), unpaidOrder.getProductName(), unpaidOrder.getTotalAmount());

            return new PayOrderRes().builder()
                    .orderId(payOrder.getOrderId())
                    .payUrl(payOrder.getPayUrl())
                    .userId(payOrder.getUserId())
                    .build();

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
        PayOrder payOrder = createPayOrder(productVO.getProductId(), orderId, productVO.getProductName(), productVO.getPrice());




        return PayOrderRes.builder()
                .orderId(orderId)
                .userId(shopCartReq.getUserId())
                .payUrl(payOrder.getPayUrl())
                .build();
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        PayOrder payOrderReq = new PayOrder();
        payOrderReq.setOrderId(orderId);
        payOrderReq.setStatus(Constants.OrderStatusEnum.PAY_SUCCESS.getCode());
        orderDao.changeOrderPaySuccess(payOrderReq);

        eventBus.post(JSON.toJSONString(payOrderReq));
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return orderDao.changeOrderClose(orderId);
    }

    @Override
    public List<String> queryNoPayNotifyOrder() {
        return orderDao.queryNoPayNotifyOrder();
    }

    @Override
    public List<String> queryTimeoutCloseOrderList() {
        return orderDao.queryTimeoutCloseOrderList();
    }

    /**
     * 创建支付单
     * @return
     */
    private PayOrder createPayOrder(String productId,String orderId, String productName,
                                    BigDecimal totalAmount) throws AlipayApiException {
        //请求
        AlipayTradePagePayRequest req = new AlipayTradePagePayRequest();
        req.setNotifyUrl(notifyUrl);
        req.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        req.setBizContent(bizContent.toString());

        //生成收款二维码
        String form = alipayClient.pageExecute(req).getBody();

        //支付单生成
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderId(orderId);
        payOrder.setPayUrl(form);
        payOrder.setStatus(Constants.OrderStatusEnum.PAY_WAIT.getCode());

        //同步到数据库
        orderDao.updateOrderPayInfo(payOrder);

        return payOrder;

    }

}
