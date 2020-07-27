package com.ddlij.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ddlij.rabbitmq.config.RabbitmqProperties;
import com.ddlij.rabbitmq.constant.Status;
import com.ddlij.rabbitmq.entity.Order;
import com.ddlij.rabbitmq.entity.OrderMessage;
import com.ddlij.rabbitmq.productor.OrderProductorServiceImpl;
import com.ddlij.rabbitmq.service.OrderMessageService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ddlij
 * @date 2020/7/16/016 12:24
 */
@Component
public class OrderReceiverService {
    private static final Logger logger = LoggerFactory.getLogger(OrderReceiverService.class);
    @Autowired
    private OrderMessageService orderMessageService;

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(value = "test-queue" ,durable ="true"),
            exchange=@Exchange(name = "order-exchange",durable ="true",type = "topic"),
            key="order.*"
    )
    )
    @RabbitHandler
    public void doMessageConsumer(@Payload JSONObject object,
                                  @Headers Map<String, Object> headers,
                                  Channel channel) throws Exception{
        System.out.println("消费JSON对象：" + JSON.toJSONString(object));
        Order order = JSONObject.toJavaObject(object, Order.class);
        OrderMessage orderMessage = orderMessageService.selectByPrimaryKey(order.getId());
        if(orderMessage == null || orderMessage.getStatusId() == Status.CONSUMERED.getIndex()){
            logger.info("重复消费，消息id{}" ,order.getId());
            return;
        }
        logger.info("手工签收消息");
        //手工签收完之后必须ack
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //ACK
        channel.basicAck(deliveryTag,false);

        OrderMessage message1 = new OrderMessage();
        message1.setStatusId(Status.CONSUMERED.getIndex());
        message1.setId(order.getId());
        orderMessageService.doUpdateMessageByCondition(message1);
        logger.info("消费确认成功，消息id{}" ,order.getId());
    }

}
