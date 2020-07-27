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
public interface OrderProductorService {

    public void send(Order order) throws Exception;

}
