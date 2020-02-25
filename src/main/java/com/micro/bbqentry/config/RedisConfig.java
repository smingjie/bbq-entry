package com.micro.bbqentry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 *
 * @author jockeys
 * @since 2020/2/23
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // key   序列化方式为 string
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value 序列化方式为 jackson
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hashKey   序列化方式为 string
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hashValue 序列化方式为 jackson
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 连接方式 选取lettuce
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
