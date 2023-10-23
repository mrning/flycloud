package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 社区任务参与过程
 * @author HZY
 * @date 2021-06-01
 */
@Data
public class CommunityTaskProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Long id;

    /**
    * 任务编号
    */
    private String taskId;

    /**
    * 记录状态 00未累计 01已累计
比如分享3次得100,前两次就是00,第三次触发01
    */
    private String processStatus;

    /**
    * 触发时间
    */
    private Date createTime;

    /**
    * 用户id
    */
    private Long ownerId;

    /**
    * 更新前的数值
    */
    private BigDecimal beforeEnergy;
    /**
     * 本次获取数值
     */
    private BigDecimal energy;

    private String keywords;

    private String appChannel;
    private String userNo;

}