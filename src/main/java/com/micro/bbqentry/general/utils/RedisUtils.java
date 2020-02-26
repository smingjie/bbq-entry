package com.micro.bbqentry.general.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Redis 操作工具类
 *
 * @author jockeys
 * @since 2020/2/23
 */
@Slf4j
@Component
public final class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取RedisTemplate实例
     */
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

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
     * @return true成功 /false失败
     */
    public boolean hdel(String key, String... hashKeys) {
        try {
            redisTemplate.opsForHash().delete(key, hashKeys);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 添加数据
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间 单位（s）
     * @return true成功 false 失败
     */
    public boolean vadd(String key, String value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取数据（字符串结果）
     *
     * @param key 键
     * @return 字符串值
     */
    public String vget(String key) {
        Object data = null;
        try {
            data = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return null;
        }
        return data.toString();
    }

    /**
     * 删除数据
     *
     * @param key 键
     * @return true成功 /false失败
     */
    public boolean vdel(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 设置key的生命周期
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 指定key在指定的日期过期
     */
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }
}
