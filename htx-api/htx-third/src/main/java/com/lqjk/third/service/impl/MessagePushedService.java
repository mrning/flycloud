package com.lqjk.third.service.impl;

import cn.hutool.json.JSONUtil;
import com.lqjk.mq.constants.MQConstant;
import com.lqjk.mq.service.RabbitMqService;
import com.lqjk.third.beans.MessageParameter;
import com.lqjk.third.beans.MessagePushed;
import com.lqjk.third.beans.ThirdChannelEnum;
import com.lqjk.third.beans.UserInformation;
import com.lqjk.third.mapper.UserInformationMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @Description 消息推送服务
 * @Author HZY
 * @Date 2019/10/22 8:49
 * @Version 1.0
 **/
@Slf4j
@Service
public class MessagePushedService {
    
    @Value("${metro.push.master}")
    String masterSecurity;
    @Value("${metro.push.appkey}")
    String appKey;
    @Value("${metro.push.htxMaster}")
    String htxMasterSecurity;
    @Value("${metro.push.htxAppkey}")
    String htxAppKey;

    @Value("${metro.push.apnsProduction}")
    private boolean apnsProduction;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private UserInformationMapper userInformationMapper;

    /*---------------------------------站内信-------------------------------------*/

    /**
     * @Author
     * @Description 发站内信
     * @Date
     * @Param [parameter]
     * @return void
     **/
    public void sendMessage(MessageParameter parameter) {
        if (parameter.getOwnerId() != null) {
            UserInformation user = userInformationMapper.findUserInformationByOwnerId(parameter.getOwnerId());
            if(user!=null){
                parameter.setUserNo(user.getUserNo());
                parameter.setAppChannel(ThirdChannelEnum.HTX_APP.getCode());
                generatePushMessage(parameter, parameter.getOwnerId());
            }
        }
    }
    //发送到队列
    private void generatePushMessage(MessageParameter parameter, Long ownerId) {
        MessagePushed messagePushed = new MessagePushed();
        messagePushed.setOwnerId(ownerId);
        messagePushed.setCreateDate(Calendar.getInstance().getTime());
        messagePushed.setMessageId(0L);
        messagePushed.setTitle(parameter.getTitle());
        messagePushed.setType(parameter.getType());
        messagePushed.setEntityId(parameter.getEntityId());
        messagePushed.setContent(parameter.getContent());
        messagePushed.setJumpType(parameter.getJumpType());
        messagePushed.setJumpParam(parameter.getJumpParam());
        messagePushed.setGeneralType(parameter.getGeneralType());
        messagePushed.setUserNo(parameter.getUserNo());
        messagePushed.setAppChannel(parameter.getAppChannel());
        if(StringUtils.isBlank(parameter.getThirdChannel())){
            if(ThirdChannelEnum.HTX_APP.getCode().equals(parameter.getAppChannel())){
                messagePushed.setThirdChannel(ThirdChannelEnum.HTX_APP.getCode());
            }
        }else{
            messagePushed.setThirdChannel(parameter.getThirdChannel());
        }
        rabbitMqService.send(MQConstant.MAIL_USER_QUEUE, JSONUtil.toJsonStr(messagePushed));
        log.info("消息已发送至队列");
    }

    /*--------------------------------------------------------------------------------------*/
}