package com.lqjk.third.beans;

import lombok.Data;

/**
 * @ClassName TradeInfoVo
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/10 14:18
 * @Version 1.0
 */
@Data
public class ShTradeInfoVo {
    /**
     * 流水号
     */
    private String travelNo;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 上车时间
     */
    private String intoTransTime;

    /**
     * 下车时间
     */
    private String outTransTime;

    /**
     * 里程数
     */
    private Long mileage;

    /**
     * 用户token
     */
    private String userToken;
}
