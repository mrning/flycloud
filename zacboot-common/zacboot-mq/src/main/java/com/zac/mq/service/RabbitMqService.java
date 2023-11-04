package com.zac.mq.service;

import cn.hutool.json.JSONUtil;
import com.zac.mq.constants.MQConstant;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author HZY
 * @Date 2020/12/3 8:47
 * @Version 1.0
 **/
@Service
public class RabbitMqService implements MqMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Override
    public void sendObj(String queueName, Object message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Override
    public void sendObj(String queueName, Object message, int times) {
    }

    @Override
    public void sendObj(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    @Override
    public void send(String queueName, String message, long times) {
        //消息发送到死信队列上，当消息超时时，会发生到转发队列上，转发队列根据下面封装的queueName，把消息转发的指定队列上
        //发送前，把消息进行封装，转发时应转发到指定 queueName 队列上
        DLXMessage dlxMessage = new DLXMessage(MQConstant.DEFAULT_EXCHANGE, queueName, message, times);
        MessagePostProcessor processor = message1 -> {
            message1.getMessageProperties().setExpiration(String.valueOf(times));
            return message1;
        };
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE, MQConstant.DEFAULT_DEAD_LETTER_QUEUE, JSONUtil.toJsonStr(dlxMessage), processor);
    }
}