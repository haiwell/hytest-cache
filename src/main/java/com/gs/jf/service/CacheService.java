package com.gs.jf.service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/2/5.
 */
public interface CacheService {

    void putData(String key, Object value, long timeout, TimeUnit timeUnit);

    Object getData(String key);

    void putData(String key,Object value);

}
