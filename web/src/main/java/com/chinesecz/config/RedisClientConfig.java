package com.chinesecz.config;


import com.chinesecz.listener.OrderPaySuccessByRedisListener;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.config.Config;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Configuration
public class RedisClientConfig {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private OrderPaySuccessByRedisListener redisTopicListener01;

    /**
     * 手动配置
     */
    @Bean("paySuccessTopic")
    public RTopic testRedisTopicListener(RedissonClient redissonClient, OrderPaySuccessByRedisListener redisTopicListener) {
        RTopic topic = redissonClient.getTopic("监听支付回调");
        topic.addListener(String.class, redisTopicListener);
        return topic;
    }

}
