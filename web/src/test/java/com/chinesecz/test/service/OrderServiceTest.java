package com.chinesecz.test.service;

import com.chinesecz.domain.req.ShopCartReq;
import com.chinesecz.domain.res.PayOrderRes;
import com.chinesecz.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Resource
    private IOrderService orderService;
    @Test
    public void test() throws Exception {
        ShopCartReq shopCartReq = new ShopCartReq();
        shopCartReq.setProductId("01");
        shopCartReq.setUserId("youcury");
        PayOrderRes payOrderRes = orderService.createOrder(shopCartReq);
        log.info("测试结果，{}",payOrderRes);

    }
}
