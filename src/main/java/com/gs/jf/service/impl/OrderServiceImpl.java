package com.gs.jf.service.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.gs.jf.mapper.OrderMapper;
import com.gs.jf.service.CacheService;
import com.gs.jf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.Cache;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

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


    @PostConstruct
    public void  initBloomFilter() {
        List<String> list = orderMapper.selectDistinctUserCode();
        bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),list.size() * 2,0.01);
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
        object = null;
        if (object == null) {
            BigDecimal sumPrice = orderMapper.selectOrderPriceByUserCode(userCode);
            cacheService.putData(cache_key,sumPrice);
            return sumPrice;
        } else {
            return new BigDecimal(String.valueOf(object));
        }
    }
}
