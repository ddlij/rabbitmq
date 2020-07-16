package com.ddlij.rabbitmq.productor.util;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author ddlij
 * @date 2020/7/12/012 19:55
 */
@Component
public class RabbitmqUtil {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqUtil.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendObjectMessage(Object object) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("11223344");
        rabbitTemplate.convertAndSend("order-exchange",  //exchange
                "order.key",  //routing-key
                JSONObject.parseObject(JSONObject.toJSON(object).toString()),  //message Object
                correlationData);
        System.out.println("消息发送成功");
    }

    public void sendMessage(String message) throws Exception{
        logger.info("rabbitUtil发送消息前的信息：{}",message);
        logger.info("template是否为空：{}",(rabbitTemplate==null));
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("55667788");
        rabbitTemplate.convertAndSend("order-exchange",  //exchange
                "order-key",  //routing-key
                message,  //message Object
                correlationData);
    }


}
