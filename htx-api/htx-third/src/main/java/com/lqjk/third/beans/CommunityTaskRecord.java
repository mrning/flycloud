package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 任务参与记录
 * @author HZY
 * @date 2021-06-01
 */
@Data
public class CommunityTaskRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Long id;

    /**
    * owner_id
    */
    private Long ownerId;

    /**
    * task_id
    */
    private String taskId;

    /**
    * 完成次数/乘车次数/正确次数
    */
    private Integer completeTimes;

    /**
    * 总能量
    */
    private BigDecimal totalEnergy;
    /**
     * 总能量
     */
    private BigDecimal realTotalEnergy;

    /**
    * 任务执行数值 总共里数/总分享次数/总答题次数
    */
    private Integer taskParam;

    /**
    * 创建日期
    */
    private Date createTime;

    /**
    * 更新日期
    */
    private Date updateTime;

    private String appChannel;
    private String userNo;

}