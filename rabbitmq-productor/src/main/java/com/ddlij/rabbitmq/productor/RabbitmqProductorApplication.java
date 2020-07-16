package com.ddlij.rabbitmq.productor;

import com.ddlij.rabbitmq.productor.config.RabbitmqProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages= {"com.ddlij.rabbitmq.*"})
@ServletComponentScan(basePackages = {"com.ddlij.rabbitmq.*"})
@EnableAutoConfiguration
public class RabbitmqProductorApplication  extends SpringBootServletInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqProductorApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProductorApplication.class, args);
    }

    @Autowired
    private RabbitmqProperties rabbitMQProperties;
    @Override
    public void run(String... args) throws Exception {
        String config = "host: " + rabbitMQProperties.getHost()
                + ", config.port:" + rabbitMQProperties.getPort()
                + ", config.userName:" + rabbitMQProperties.getUsername();

        log.info("SpringBoot2.0实现自定义properties配置文件与JavaBean映射: {}" , config);
    }

}
