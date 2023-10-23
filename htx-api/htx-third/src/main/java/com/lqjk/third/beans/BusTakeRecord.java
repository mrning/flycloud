package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 公交行程记录表
 * @author nxr
 * @date 2021-08-06
 */
@Data
public class BusTakeRecord implements Serializable {

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
	 * 乘坐日期
	 */
	private String transDate;

	/**
	 * 乘坐时间
	 */
	private String transTime;

	/**
	 * 乘坐线路
	 */
	private String transLine;

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
	
	private String currentDate;

	/**
	 * 区号0532青岛
	 */
	private String city;

	private String longevityChainFlag;

	private BigDecimal carbon;

	private Long teamNo;

	private String appChannel;
	private String userNo;
	private String thirdChannel;
	private String source;

	private UserAuthCardNo userAuthCardNo;

}
