package com.ddlij.rabbitmq.productor.entity;

import java.io.Serializable;

/**
 * @author ddlij
 * @date 2020/7/11/011 23:04
 */
public class Order implements Serializable {

    private String id;

    private String name;

    private String messageId;  //消息id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }


}
