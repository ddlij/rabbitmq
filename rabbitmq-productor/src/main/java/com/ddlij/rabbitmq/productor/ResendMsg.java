package com.ddlij.rabbitmq.productor;

import com.alibaba.fastjson.JSONObject;
import com.ddlij.rabbitmq.constant.Status;
import com.ddlij.rabbitmq.entity.OrderMessage;
import com.ddlij.rabbitmq.service.OrderMessageService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ddlij
 * @date 2020/7/27/027 9:34
 */
@Component
public class ResendMsg {
    private static final Logger log = LoggerFactory.getLogger(OrderProductorService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMessageService orderMessageService;

    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;

    /**
     * 注册一个 名字为:SchedulePool 线程池
     *
     * @author ming
     * @date 2019-07-22 14:16:44
     */
    @Bean("SchedulePool")
    public ThreadPoolExecutor schedulePool() {
        return new ScheduledThreadPoolExecutor(10,
                new BasicThreadFactory.Builder().namingPattern("Scheduling-%d").daemon(true).build());
    }

    /**
     * 每30s拉取投递失败的消息, 重新投递
     * 使用 @Async(value = "SchedulePool") 将任务异步化
     *
     * @author ddlij
     * @date 2019-07-22 14:17:02
     */
    @Async(value = "SchedulePool")
    @Scheduled(cron = "0/30 * * * * ?")
    public void resend() {
        log.info("开始执行定时任务(重新投递消息)");

        OrderMessage message1 = new OrderMessage();
        message1.setStatusId(Status.SENDING.getIndex());
        List<OrderMessage> msgList = orderMessageService.selectByCondition(message1);
        msgList.forEach(msgLog -> {
            long msgId = msgLog.getId();
            if (msgLog.getTryCount() >= MAX_TRY_COUNT) {
                OrderMessage message2 = new OrderMessage();
                message2.setStatusId(Status.DELETE.getIndex());
                message2.setId(msgId);
                orderMessageService.doUpdateMessageByCondition(message2);
                log.info("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            } else {
                OrderMessage message2 = new OrderMessage();
                message2.setId(msgId);
                message2.setTryCount(msgLog.getTryCount() + 1);

                String errorMsg = null;
                try {
                    CorrelationData correlationData = new CorrelationData(msgId + "");
                    rabbitTemplate.convertAndSend(msgLog.getExchangeName(), msgLog.getRoutingKey(), JSONObject.parseObject(msgLog.getMessage()), correlationData);// 重新投递
                    message2.setStatusId(Status.SUCCESS.getIndex());
                }catch (Exception e) {
                    e.printStackTrace();
                    errorMsg = e.getMessage();
                }finally {
                    message2.setErrorMsg(errorMsg);
                    orderMessageService.doUpdateMessageByCondition(message2);
                }

                log.info("第 " + message2.getTryCount() + " 次重新投递消息");
            }
        });

        log.info("定时任务执行结束(重新投递消息)");
    }

}
