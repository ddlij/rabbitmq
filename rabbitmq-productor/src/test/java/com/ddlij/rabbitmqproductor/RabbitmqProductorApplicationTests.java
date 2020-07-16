package com.ddlij.rabbitmqproductor;

import com.ddlij.rabbitmq.productor.RabbitmqProductorApplication;
import com.ddlij.rabbitmq.productor.entity.Order;
import com.ddlij.rabbitmq.productor.service.OrderProductor;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqProductorApplication.class)
class RabbitmqProductorApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testSend1() throws Exception{
        OrderProductor orderProductor = new OrderProductor();
        Order order = new Order();
        order.setId("11223344");
        order.setName("ddlij");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderProductor.send(order);
    }

    @Test
    public void testBasicPublish() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明一个接收被删除的消息的交换机和队列
        String EXCHANGE_DEAD_NAME = "exchange.dead";
        String QUEUE_DEAD_NAME = "queue_dead";
        channel.exchangeDeclare(EXCHANGE_DEAD_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(QUEUE_DEAD_NAME, false, false, false, null);
        channel.queueBind(QUEUE_DEAD_NAME, EXCHANGE_DEAD_NAME, "routingkey.dead");

        // 声明一个接收消息的交换机和队列
        String EXCHANGE_NAME = "order-exchange";
        String QUEUE_NAME = "order-queue";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-message-ttl", 15000);
        arguments.put("x-max-length", 4);
        arguments.put("x-max-length-bytes", 1024);
        arguments.put("x-expires", 30000);
        arguments.put("x-dead-letter-exchange", "exchange.dead");
        arguments.put("x-dead-letter-routing-key", "routingkey.dead");
        channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
        String message = "Hello RabbitMQ: ";
        for(int i = 1; i <= 5; i++) {
            channel.basicPublish(EXCHANGE_NAME, "", null, (message + i).getBytes("UTF-8"));
        }
        channel.close();
        connection.close();
    }

}
