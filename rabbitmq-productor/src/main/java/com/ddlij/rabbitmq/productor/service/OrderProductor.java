package com.ddlij.rabbitmq.productor.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.ddlij.rabbitmq.productor.config.RabbitmqProperties;
import com.ddlij.rabbitmq.productor.entity.Order;
import com.ddlij.rabbitmq.productor.util.RabbitmqUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;
import java.util.UUID;

/**
 * @author ddlij
 * @date 2020/7/12/012 12:39
 */
@Component
public class OrderProductor{
    private static final Logger logger = LoggerFactory.getLogger(OrderProductor.class);
    @Autowired
    private RabbitmqUtil rabbitmqUtil;
    public void send(Order order) throws Exception{
        logger.info("需要发送的消息：" + JSON.toJSONString(order));
        rabbitmqUtil.sendObjectMessage(order);
    }

}
