package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 行程记录表
 * @author liuchijng
 * @date 2021-06-01
 */
@Data
public class MetroTakeRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据记录信息ID
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 进站时间
	 */
	private Date entryDate;

	/**
	 * 进站
	 */
	private String entryStation;

	/**
	 * 出站时间
	 */
	private Date exitDate;

	/**
	 * 出站
	 */
	private String exitStation;

	/**
	 * 途经车站个数
	 */
	private Integer stationCount;

	/**
	 * 里程 单位米
	 */
	private BigDecimal transMileage;

	/**
	 * 行程订单号
	 */
	private String transOrderNo;

	/**
	 * 01已上链 02已前置 03已收取
	 */
	private String status;

	/**
	 * 数据是否不可用
	 */
	private Boolean disable;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 数据上链标识 01-历史数据(不上链)  02-初始化数据(待发送消息队列) 03-上链成功 04-上链失败 05-已发送至消息队列
	 */
	private String longevityChainFlag;

	/**
	 * 区号0532青岛
	 */
	private String city;

	private BigDecimal carbon;
	
	private String currentDate;

	private Long teamNo;
	private String appChannel;
	private String userNo;

	private String thirdChannel;
	private String source;

	private Boolean stationFalg =false;
}
