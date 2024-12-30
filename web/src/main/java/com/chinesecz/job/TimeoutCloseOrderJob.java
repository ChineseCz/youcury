package com.chinesecz.job;


import com.chinesecz.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 超时关单
 * @create 2024-09-30 09:59
 */
@Slf4j
@Component()
public class TimeoutCloseOrderJob {

    @Resource
    private IOrderService orderService;
    @Qualifier("threadPoolExecutor01")
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor01;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void exec() {
        threadPoolExecutor01.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("任务；超时30分钟订单关闭");
                    List<String> orderIds = orderService.queryTimeoutCloseOrderList();
                    if (null == orderIds || orderIds.isEmpty()) {
                        log.info("定时任务，超时30分钟订单关闭，暂无超时未支付订单 orderIds is null");
                        return;
                    }
                    for (String orderId : orderIds) {
                        boolean status = orderService.changeOrderClose(orderId);
                        log.info("定时任务，超时30分钟订单关闭 orderId: {} status：{}", orderId, status);
                    }
                } catch (Exception e) {
                    log.error("定时任务，超时15分钟订单关闭失败", e);
                }
            }
        });
    }

}
