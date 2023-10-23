package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 站间距
 * @author zhengkai.blog.csdn.net
 * @date 2021-06-16
 */
@Data
public class StationDistance implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 进站编码
	 */
	private String inStationId;

	/**
	 * 进站名称
	 */
	private String inStationName;

	/**
	 * 出站编码
	 */
	private String outStationId;

	/**
	 * 出站名称
	 */
	private String outStationName;

	/**
	 * 站间距
	 */
	private String stationDistance;

	private BigDecimal carbon;

}