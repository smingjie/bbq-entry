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
     * 设置过期时间
     *
     * @param key  键
     * @param time 过期时间
     * @return true成功 false 失败
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
                return true;
            } else {
                log.info("有效期设置失败，默认为无限期");
                return false;
            }
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 添加数据
     *
     * @param key   键
     * @param value 值
     * @return true成功 false 失败
     */
    public boolean add(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 添加数据并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean add(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                log.info("有效期设置失败，默认为无限期");
                add(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Object data = null;
        try {
            data = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return null;
        }
        return data;
    }

    /**
     * 删除数据
     *
     * @param key 键
     */
    public boolean delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis操作异常:{}", e.getMessage());
            return false;
        }
        return true;
    }
}
