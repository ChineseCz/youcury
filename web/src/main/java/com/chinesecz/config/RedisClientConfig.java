package com.chinesecz.config;


import com.chinesecz.listener.OrderPaySuccessByRedisListener;
import com.chinesecz.service.redis.IRedisService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
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
import java.util.concurrent.TimeUnit;


@Configuration
public class RedisClientConfig {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private OrderPaySuccessByRedisListener redisTopicListener01;

    @Resource
    private IRedisService redisService;
    /**
     * 主题
     */
    @Bean("paySuccessTopic")
    public RTopic testRedisTopicListener(RedissonClient redissonClient, OrderPaySuccessByRedisListener redisTopicListener) {
        RTopic topic = redissonClient.getTopic("监听支付回调");
        topic.addListener(String.class, redisTopicListener);
        return topic;
    }

    /**
     * 缓存
     * @return
     */
    @Bean(name = "redisWeixinAccessToken")
    public RMap<String, String> weixinAccessToken() {
        return redissonClient.getMap("weixinAccessToken");
    }

    @Bean(name = "redisOpenidToken")
    public RMap<String, String> openidToken() {
        return redissonClient.getMap("openidToken");
    }

}
