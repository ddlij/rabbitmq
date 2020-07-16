package com.ddlij.rabbitmq.productor.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.ddlij.rabbitmq.productor.entity.Order;
import com.ddlij.rabbitmq.productor.service.OrderProductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ddlij
 * @date 2020/7/12/012 20:08
 */
@RestController
public class RabbitmqRestController {

    @Autowired
    private OrderProductor orderProductor;

    @RequestMapping(value="/com/ddlij/rabbitmq/testSendMessage.json",method = RequestMethod.POST)
    public String toListPage(HttpServletRequest request) throws Exception{
        Order order = new Order();
        String name = request.getParameter("name");
        if(name != null){
            order.setName(name);
        }
        String messageId = request.getParameter("messageId");
        if(messageId != null){
            order.setMessageId(messageId);
        }
        try {
            order.setId(System.currentTimeMillis() + "$");
            orderProductor.send(order);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(order);
    }
}
