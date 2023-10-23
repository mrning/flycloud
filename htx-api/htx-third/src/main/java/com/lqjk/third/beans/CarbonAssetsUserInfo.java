package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author nxr
 * @date 2021-06-01
 */
@Data
public class CarbonAssetsUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Long ownerId;

    /**
     * 累计碳资产
     */
    private BigDecimal totalCarbonAssetsGram;

    private BigDecimal realTotalCarbonAssetsGram;

    /**
     * 剩余碳资产
     */
    private BigDecimal balanceCarbonAssetsGram;

    /**
     * 碳资产类型 01=碳 02=精力值
     */
    private String carbonAssetsTypeCode;

    /**
     * 累计兑换金额
     */
    private Integer totalCarbonAssetsAmt;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastUpdateDate;

    private String appChannel;
    private String userNo;


    /**
     * 从青岛地铁端内H5收集的碳资产总和
     */
    private BigDecimal totalQdmetro;

}