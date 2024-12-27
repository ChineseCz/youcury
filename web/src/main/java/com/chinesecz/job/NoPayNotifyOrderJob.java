package com.chinesecz.job;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayAccount;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.chinesecz.dao.IOrderDao;
import com.chinesecz.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class NoPayNotifyOrderJob {
    @Resource
    private IOrderService orderService;

    @Resource
    private AlipayClient alipayClient;

    @Scheduled(cron = "0/3 * * * * ?")
    public void exec() {
        try {
            log.info("检测有无未支付订单，或者支付回调未正确处理");
            List<String> orderIds = orderService.queryNoPayNotifyOrder();
            if (null == orderIds || orderIds.isEmpty()) {
                log.info("无未支付订单");
                return;
            }

            for (String orderId:orderIds) {
                //创建请求
                AlipayTradeQueryRequest req = new AlipayTradeQueryRequest();
                AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();

                bizModel.setOutTradeNo(orderId);
                req.setBizModel(bizModel);
                //查询订单状态
                AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(req);

                String code = alipayTradeQueryResponse.getCode();
                if (code.equals("10000"))
                    orderService.changeOrderPaySuccess(orderId);

            }


        } catch (Exception e) {
            log.error("异常");
        }
    }

}
