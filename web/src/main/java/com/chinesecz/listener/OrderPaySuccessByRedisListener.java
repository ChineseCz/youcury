package com.chinesecz.listener;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderPaySuccessByRedisListener implements MessageListener<String> {

    @Override
    public void onMessage(CharSequence channel, String msg) {
        log.info("监听支付成功消息(Redis): {}", msg);
    }

}
