package com.lqjk.third.beans;

import lombok.Data;

/**
 * @ClassName T3CarTravelVo
 * @Description TODO
 * @Author YJD
 * @Date 2023/8/1 13:44
 * @Version 1.0
 */
@Data
public class T3CarTravelVo {

    private String phone;
    private String orderId;
    private Long drivingMileage;
    private String startTime;
    private String endTime;
    private String cityCode;
    private Long ownerId;
    private String userNo;
    private String appChannel;
}
