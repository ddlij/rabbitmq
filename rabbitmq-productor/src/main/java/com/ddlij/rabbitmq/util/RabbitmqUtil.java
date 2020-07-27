package com.ddlij.rabbitmq.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ddlij
 * @date 2020/7/12/012 19:55
 */
@Component
public class RabbitmqUtil {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqUtil.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String EXCHANGE_NAME = "order-exchange";
    private String ROUTING_KEY = "order.key";
    private String QUEUE_NAME = "test";

    /**
     * 向mq推送消息
     * @param object
     * @throws Exception
     */
    public void sendObjectMessage(Object object) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(System.currentTimeMillis() + "corr");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,  //exchange
                ROUTING_KEY,  //routing-key
                JSONObject.parseObject(JSONObject.toJSON(object).toString()),  //message Object
                correlationData);
        System.out.println("消息发送成功");
    }

    public void sendMessage(String message) throws Exception{
        logger.info("rabbitUtil发送消息前的信息：{}",message);
        logger.info("template是否为空：{}",(rabbitTemplate==null));
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("55667788");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,  //exchange
                ROUTING_KEY,  //routing-key
                message,  //message Object
                correlationData);
    }

}
