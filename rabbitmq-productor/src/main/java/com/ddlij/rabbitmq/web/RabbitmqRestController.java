package com.ddlij.rabbitmq.web;

import com.alibaba.fastjson.JSON;
import com.ddlij.rabbitmq.entity.Order;
import com.ddlij.rabbitmq.productor.OrderProductorService;
import com.ddlij.rabbitmq.util.SnowFlakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ddlij
 * @date 2020/7/12/012 20:08
 */
@RestController
public class RabbitmqRestController {

    @Autowired
    private OrderProductorService orderProductorService;

    @RequestMapping(value="/com/ddlij/rabbitmq/testSendMessage.json",method = RequestMethod.POST)
    public String toListPage(HttpServletRequest request) throws Exception{
        Order order = new Order();
        String name = request.getParameter("name");
        if(name != null){
            order.setName(name);
        }
        String messageId = request.getParameter("messageId");
        if(messageId != null){
            order.setMessageId(Long.parseLong(messageId.trim()));
        }
        try {
            order.setId(SnowFlakeGenerator.getNextId());
            orderProductorService.send(order);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(order);
    }
}
