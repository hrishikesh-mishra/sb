package com.hrishikeshmishra.bj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@Configuration
public class RedisConfig {

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(connectionFactory());
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory(sentinelConfig());
    }

    @Bean
    public RedisSentinelConfiguration sentinelConfig() {
        return  new RedisSentinelConfiguration().
                master("mymaster").
                sentinel("127.0.0.1", 26379);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
