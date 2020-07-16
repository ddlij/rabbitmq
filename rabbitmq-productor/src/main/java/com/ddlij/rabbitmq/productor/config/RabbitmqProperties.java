package com.ddlij.rabbitmq.productor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ddlij
 * @date 2020/7/12/012 19:12
 */
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
@PropertySource(value="classpath:application.properties",encoding="utf-8")
public class RabbitmqProperties {
    private String host;

    private int port;

    private String username;

    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
