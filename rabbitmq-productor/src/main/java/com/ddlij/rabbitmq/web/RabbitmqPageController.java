package com.ddlij.rabbitmq.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ddlij
 * @date 2020/7/12/012 20:08
 */
@Controller
public class RabbitmqPageController {

    @RequestMapping(value="/toSendPage.web",method = RequestMethod.GET)
    public String toListPage(Model model, HttpServletRequest request){
        String systemctx = request.getContextPath();
        model.addAttribute("systemctx",systemctx);
        return "rabbitmq/testRabbitmq";
    }
}
