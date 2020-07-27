package com.ddlij.rabbitmq.service;

import com.ddlij.rabbitmq.dao.OrderMessageMapper;
import com.ddlij.rabbitmq.entity.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author ddlij
 * @date 2020/7/12/012 12:39
 */
@Service("orderMessageService")
public class OrderMessageServiceImpl implements OrderMessageService{
    private static final Logger logger = LoggerFactory.getLogger(OrderMessageServiceImpl.class);
    @Autowired
    private OrderMessageMapper orderMessageMapper;

    /**
     * 新增消息入库记录
     * @param message
     * @return
     */
    public OrderMessage doSaveMessage(OrderMessage message){
        if(message.getId() == null){
            message.setId(System.currentTimeMillis());
        }
        message.setStatusId(1);
        message.setTryCount(0);
        message.setCreatTime(new Timestamp(System.currentTimeMillis()));
        message.setModifyTime(message.getCreatTime());

        orderMessageMapper.insert(message);

        return message;
    }

    /**
     * 修改消息状态
     * @param orderMessage
     * @return
     */
    public int doUpdateMessageByCondition(OrderMessage orderMessage){
        orderMessage.setModifyTime(new Timestamp(System.currentTimeMillis()));
        int success = orderMessageMapper.updateByPrimaryKeySelective(orderMessage);
        return success;
    }

    /**
     * 条件查询结果集
     * @param message
     * @return
     */
    public List<OrderMessage> selectByCondition(OrderMessage message){
        return orderMessageMapper.selectByCondition(message);
    }


    public OrderMessage selectByPrimaryKey(Long id){
        return orderMessageMapper.selectByPrimaryKey(id);
    }
}
