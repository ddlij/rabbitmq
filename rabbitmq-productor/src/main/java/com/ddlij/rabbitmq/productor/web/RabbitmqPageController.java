package com.ddlij.rabbitmq.productor.web;

import com.ddlij.rabbitmq.productor.service.OrderProductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fyzheng
 * @date 2020/7/12/012 20:08
 */
@Controller
public class RabbitmqPageController {

    @Autowired
    private OrderProductor orderProductor;

    @RequestMapping(value="/toSendPage.web",method = RequestMethod.GET)
    public String toListPage(Model model, HttpServletRequest request){
        String systemctx = request.getContextPath();
        model.addAttribute("systemctx",systemctx);
        return "rabbitmq/testRabbitmq";
    }
}
