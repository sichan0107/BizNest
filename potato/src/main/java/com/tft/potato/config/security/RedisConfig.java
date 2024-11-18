package com.tft.potato.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /*
        RedisRepository보다 RedisTemplate이 더 자주 쓰이는 이유
        1. RedisRepo는 Domain Entity를 간단하게 Redis Hash로 만들 수 있다.
         - 그러나 트랜잭션을 지원하지 않기 때문에 트랜잭션을 적용하려면 RedisTemplate을 써야한다.
        2. 특정 Entity 뿐만 아니라 여러가지 원하는 타입을 넣을 수 있다.
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

}