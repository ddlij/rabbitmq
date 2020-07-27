package com.ddlij.rabbitmq.productor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ddlij.rabbitmq.config.RabbitmqProperties;
import com.ddlij.rabbitmq.constant.Status;
import com.ddlij.rabbitmq.entity.Order;
import com.ddlij.rabbitmq.entity.OrderMessage;
import com.ddlij.rabbitmq.service.OrderMessageService;
import com.ddlij.rabbitmq.util.RabbitmqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ddlij
 * @date 2020/7/12/012 12:39
 */
@Service("orderProductorService")
public class OrderProductorServiceImpl implements OrderProductorService{
    private static final Logger logger = LoggerFactory.getLogger(OrderProductorServiceImpl.class);
    @Autowired
    private RabbitmqUtil rabbitmqUtil;
    @Autowired
    private OrderMessageService orderMessageService;
    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    public void send(Order order) throws Exception{
        logger.info("需要发送的消息：" + JSON.toJSONString(order));
        logger.info("消息入库开始" );
        RabbitmqUtil util = new RabbitmqUtil();
        OrderMessage message = new OrderMessage();
        message.setId(order.getId());
        message.setMessage(JSONObject.toJSON(order).toString());
        message.setExchangeName(rabbitmqProperties.getExchangeName());
        message.setQueueName(rabbitmqProperties.getQueueName());
        message.setRoutingKey(rabbitmqProperties.getRoutingKey());

        message = orderMessageService.doSaveMessage(message);
        logger.info("消息入库结束");
        try {
            rabbitmqUtil.sendObjectMessage(order);
        }catch (Exception e) {
            logger.info("发送消息失败，修改消息状态为发送中");
            OrderMessage message1 = new OrderMessage();
            message1.setStatusId(Status.SENDING.getIndex());
            message1.setId(message.getId());
            message1.setErrorMsg(e.getMessage());
            orderMessageService.doUpdateMessageByCondition(message1);
        }

    }

}
