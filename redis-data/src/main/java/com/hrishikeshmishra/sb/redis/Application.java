package com.hrishikeshmishra.sb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    static final RedisSentinelConfiguration SENTINEL_CONFIG =
            new RedisSentinelConfiguration().master("mymaster").sentinel("localhost", 26379);

    @Autowired
    RedisConnectionFactory factory;

    @Autowired
    StringRedisTemplate template;

    @Override
    public void run(String... args) throws Exception {

        ValueOperations<String, String> ops = this.template.opsForValue();

        String key = "spring.boot.redis.test";
        if (!this.template.hasKey(key)) {
            ops.set(key, "foo");
        }

        System.out.println("Found key " + key + ", value=" + ops.get(key));
    }

    public static void main(String[] args) throws Exception {


        SpringApplication.run(Application.class, args).close();
    }

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
        return SENTINEL_CONFIG;
    }


}
