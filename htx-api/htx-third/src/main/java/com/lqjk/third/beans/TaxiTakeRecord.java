package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName TaxiTakeRecord
 * @Description 出租车出行记录
 * @Author YJD
 * @Date 2023/8/1 14:02
 * @Version 1.0
 */
@Data
public class TaxiTakeRecord implements Serializable {
    private Long id;
    private Long userId;
    /**
     * 行程订单号
     */
    private String transOrderNo;

    private Date startTime;

    private Date endTime;

    private Long drivingMileage;

    private Date createTime;

    private Long carbon;

    /**
     * 数据上链标识 01-历史数据(不上链)  02-初始化数据(待发送消息队列) 03-上链成功 04-上链失败 05-已发送至消息队列
     */
    private String longevityChainFlag;

    private Long teamNo;
    private String appChannel;

    private String thirdChannel;
    private String source;
}
