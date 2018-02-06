package com.gs.jf.service;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/2/6.
 */
public interface OrderService {

    BigDecimal selectOrderPriceByUserCode(String userCode);

}
