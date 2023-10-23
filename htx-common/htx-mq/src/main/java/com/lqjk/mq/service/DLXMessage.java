package com.lqjk.mq.service;

import lombok.Data;

/**
 * @Description 死信消息载体
 * @Author HZY
 * @Date 2019/10/22 11:03
 * @Version 1.0
 **/
@Data
public class DLXMessage {

    private String exchange;
    private String queueName;
    private String content;
    private long times;

    public DLXMessage(String queueName, String content, long times) {
        super();
        this.queueName = queueName;
        this.content = content;
        this.times = times;
    }

    public DLXMessage(String exchange, String queueName, String content, long times) {
        super();
        this.exchange = exchange;
        this.queueName = queueName;
        this.content = content;
        this.times = times;
    }
}