package com.chinesecz.listener;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisTopicListener01 implements MessageListener<String> {

    @Override
    public void onMessage(CharSequence channel, String msg) {
        log.info("01-监听消息(Redis 发布/订阅): {}", msg);
    }

}
