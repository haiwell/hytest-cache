package com.gs.jf.mapper;

import com.gs.jf.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    BigDecimal selectOrderPriceByUserCode(String userCode);

    List<String> selectDistinctUserCode();

}