package com.chinesecz.config;


import com.chinesecz.listener.RedisTopicListener01;
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

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description Redis 客户端，使用 Redisson <a href="https://github.com/redisson/redisson">Redisson</a>
 * @create 2023-09-09 16:51
 */
@Configuration
public class RedisClientConfig {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTopicListener01 redisTopicListener01;

    /**
     * 手动配置
     */
    @Bean("testRedisTopic")
    public RTopic testRedisTopicListener(RedissonClient redissonClient, RedisTopicListener01 redisTopicListener) {
        RTopic topic = redissonClient.getTopic("xfg-dev-tech-topic");
        topic.addListener(String.class, redisTopicListener);
        return topic;
    }

}
