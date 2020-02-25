package com.micro.bbqentry.general.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * Redis 操作工具类
 *
 * @author jockeys
 * @since 2020/2/23
 */
@Slf4j
@Component
public final class RedisUtility {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 添加数据到hash结构
     *
     * @param key       键
     * @param hashKey   hash键
     * @param hashValue hash值
     * @return true成功 false 失败
     */
    public boolean hadd(String key, String hashKey, String hashValue) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, hashValue);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
    }


    /**
     * 获取hash结构的数据
     *
     * @param key     键
     * @param hashKey hash键
     * @return hash值
     */
    public Object hget(String key, String hashKey) {
        Object data = null;
        try {
            data = redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return null;
        }
        return data;
    }

    /**
     * 删除数据
     *
     * @param key      键
     * @param hashKeys hash键（more）
     */
    public boolean delete(String key, String... hashKeys) {
        try {
            redisTemplate.opsForHash().delete(key, hashKeys);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
        return true;
    }
}
