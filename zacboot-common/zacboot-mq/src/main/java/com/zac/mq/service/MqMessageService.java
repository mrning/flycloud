package com.zac.mq.service;

/**
 * @Description 消息生成接口
 * @Author HZY
 * @Date 2019/10/21 16:49
 * @Version 1.0
 **/

public interface MqMessageService {

    /**
     * @Author HZY
     * @Description 发送消息到队列
     * @Date 16:50 2019/10/21
     * @Param [queueName 队列名称, message 消息内容]
     * @return void
     **/
    void send(String queueName, String message);

    void sendObj(String queueName, Object message);

    void sendObj(String queueName, Object message, int times);


    void sendObj(String exchange, String routingKey, Object message);

    void send(String queueName, String message, long times);
}