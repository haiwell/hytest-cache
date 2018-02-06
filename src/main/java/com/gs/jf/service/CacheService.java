package com.gs.jf.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/2/5.
 */
public interface CacheService {

    void putData(String key, Object value, long timeout, TimeUnit timeUnit);

    Object getData(String key);

    void putData(String key,Object value);

    <V> V cacheGetResult(String key,String cacheName);

    void cacheRemove(String key,String cacheName);

    public <V> void cachePut(String key,V value,String cacheName);

}
