package com.lqjk.third.beans;

import com.lqjk.base.annotation.AutoColumn;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 所有能量前置
 * @Author HZY
 * @Date 2021/5/31 14:51
 * @Version 1.0
 **/
@Data
public class EnergyFrontData {

    /**
     * id
     */
    private Long id;

    /**
     * 归属用户
     */
    private Long ownerId;

    /**
     * 能量类型
     */
    private String energyType;

    /**
     * 能量场景
     */
    private String energyScene;

    /**
     * 能量值（带有膨胀）
     */
    private BigDecimal energyValue;

    /**
     * 真正的能量
     */

    private BigDecimal realEnergyValue;

    /**
     * 来源
     */
    private String energySource;

    /**
     * 单位
     */
    private String energyUnit;

    /**
     * 状态 已收取/未收取
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 收取时间
     */
    private Date collectTime;

    /**
     * 是否不可用
     */
    private Boolean disable;

    /**
     * 备注
     */
    private String remark;

    private String img;

    private String appChannel;

    private String userNo;

    private String thirdChannel;

    private String transOrderNo;

    private Long mileage;

    @AutoColumn(isIgnore = true)
    private CommunityTaskRecordThird communityTaskRecord;
}