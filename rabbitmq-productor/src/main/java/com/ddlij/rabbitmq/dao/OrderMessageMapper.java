package com.ddlij.rabbitmq.dao;

import com.ddlij.rabbitmq.entity.OrderMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderMessage record);

    int insertSelective(OrderMessage record);

    OrderMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderMessage record);

    int updateByPrimaryKey(OrderMessage record);

    List<OrderMessage> selectByCondition(OrderMessage orderMessage);
}