package com.ddlij.rabbitmq.service;

import com.ddlij.rabbitmq.entity.OrderMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface OrderMessageService {
    /**
     * 新增消息入库记录
     * @param message
     * @return
     */
    public com.ddlij.rabbitmq.entity.OrderMessage doSaveMessage(OrderMessage message);

    /**
     * 修改消息状态
     * @param orderMessage
     * @return
     */
    public int doUpdateMessageByCondition(OrderMessage orderMessage);

    /**
     * 条件查询结果集
     * @param message
     * @return
     */
    public List<OrderMessage> selectByCondition(OrderMessage message);

    public OrderMessage selectByPrimaryKey(Long id);
}