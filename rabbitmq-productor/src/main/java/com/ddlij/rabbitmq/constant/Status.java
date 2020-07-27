package com.ddlij.rabbitmq.constant;

/**
 * @author ddlij
 * @date 2020/7/26/026 17:39
 */
public enum Status{
    DELETE(0, "投递失败"),
    SUCCESS(1, "投递成功"),
    CONSUMERED(2,"已消费"),
    SENDING(3,"投递中");

    private Status(int index,String name){
        this.index = index;
        this.name = name;
    }
    /** 常量值标识(索引) */
    private int index = -1;// 索引 标识
    /** 名称 */
    private String name = "";// 名称

    /** 获取常量值 */
    public int getIndex() {
        return index;
    }

    /** 获取常量对应的名称(中文) */
    public String getName() {
        return name;
    }
}
