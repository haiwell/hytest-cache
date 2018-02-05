package com.gs.jf.service.impl;

import com.gs.jf.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/2/5.
 */
@Service
public class CacheServiceImpl implements CacheService{

    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public void putData(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
            if (timeout > 0) {
                boundValueOperations.set(value,timeout,timeUnit);
            } else {
                boundValueOperations.set(value);
            }
        } catch (Exception e) {
            logger.error("将数据放到缓存中异常" ,e);
        }
    }

    @Override
    public void putData(String key, Object value) {
        try {
            BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
            boundValueOperations.set(value);
        } catch (Exception e) {
            logger.error("将数据放到缓存中异常" ,e);
        }
    }

    @Override
    public Object getData(String key) {
        Object  object= null;
        try {
            BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
            object = boundValueOperations.get();
        } catch (Exception e) {
            logger.error("获取数据异常" ,e);
        }
        return object;
    }
}
