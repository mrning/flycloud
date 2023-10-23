package com.lqjk.third.beans;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 至臻社区任务
 * @author HZY
 * @date 2021-06-01
 */
@Data
public class CommunityTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
    * 任务描述
    */
    private String taskDesc;

    /**
    * 任务标题
    */
    private String taskTitle;

    /**
    * 任务奖励 减排量/精力值
    */
    private BigDecimal taskReward;

    /**
    * 奖励规则 比如3次xxx积分,这里就填写3
每公里100g,这里就填1
    */
    private Integer rewardRules;

    /**
    * 获取奖励上限
    */
    private Integer rewardLimit;

    /**
    * 任务类型
    */
    private String taskType;

    /**
    * create_time
    */
    private Date createTime;

    /**
    * 开始时间
    */
    private Date startDate;

    /**
    * 结束时间
    */
    private Date endDate;

    /**
    * 是否不可用
    */
    private Boolean disable;
    /**
     * 场景
     */
    private String scene;

    private String recordId;

    private boolean clickEnable;

    private String linkUrl;

    private String funDesc;

    private String logo;
    
    private boolean needPermission;

    private String scheme;

    private int dailyCount;


    /*----------任务详情------------*/
    private String alertTitle;

    private String alertDesc;

    private String rewardDesc;

    private String jumpType;

    private String jumpLink;

    private JSONObject alert;
    /*----------任务详情------------*/
    private String remark;

    /*----------任务类型-----------*/
    private String type; //1至臻社区 2上西天活动

    private String sort;

    private String gifCode;

    private String titleFontColor;
    /**
     * 是否沉浸式展示0否1是
     */
    private Integer immersionFlag;
    /**
     * 状态栏颜色
     */
    private String statusBarColor;

    private String appChannel;

    private String image;

}