package com.gs.jf.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.gs.jf.mapper.OrderMapper;
import com.gs.jf.service.CacheService;
import com.gs.jf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/2/6.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    private final static String CACHE_KEY_OrderPriceByUserCode = "CACHE_KEY_OrderPriceByUserCode_";

    @Autowired
    private CacheService cacheService;

    private BloomFilter bf = null;

    private final static Lock lock = new ReentrantLock();//可重入锁


    @PostConstruct
    public void initBloomFilter() {
        List<String> list = orderMapper.selectDistinctUserCode();
        bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), list.size() * 2, 0.01);
        for (String itemString : list) {
            bf.put(itemString);
        }
    }

    @Override
    public BigDecimal selectOrderPriceByUserCode(String userCode) {
        String cache_key = CACHE_KEY_OrderPriceByUserCode + userCode;
        if (!bf.mightContain(cache_key)) {
            return BigDecimal.ZERO;
        }
        Object object = cacheService.getData(cache_key);
        if (object != null) {
            return new BigDecimal(String.valueOf(object));
        }

        //解决缓存雪崩，热点数据持续高并发
        /**
         * 问题：
         * 1、锁的粒度问题（锁的粒度可以到用户级别）
         * 2、解决不了分布式环境并发的问题 （使用分布式锁技术zk redlock）
         * 3、线程被阻塞，用户体验变差 （使用双缓存策略，当获得锁的线程从数据库取，trylock没有获得锁的，从备份缓存中取）
         */
        lock.lock();
        try {
            object = cacheService.getData(cache_key);
            if (object != null) {
                return new BigDecimal(String.valueOf(object));
            }
            BigDecimal sumPrice = orderMapper.selectOrderPriceByUserCode(userCode);
            cacheService.putData(cache_key, sumPrice);
            return sumPrice;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }



}
