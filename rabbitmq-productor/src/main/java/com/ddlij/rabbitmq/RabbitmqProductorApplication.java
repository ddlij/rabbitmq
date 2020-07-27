package com.ddlij.rabbitmq;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ddlij.rabbitmq.dao")
@ServletComponentScan(basePackages = {"com.ddlij.rabbitmq.*"})
@EnableScheduling
public class RabbitmqProductorApplication extends SpringBootServletInitializer{
    private static final Logger log = LoggerFactory.getLogger(RabbitmqProductorApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProductorApplication.class, args);
    }


}
