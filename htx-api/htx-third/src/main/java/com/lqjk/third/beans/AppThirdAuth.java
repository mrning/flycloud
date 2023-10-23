package com.lqjk.third.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 三方APP授权记录表
 * </p>
 *
 * @author libatou
 * @since 2021-11-19
 */
@Data
@Accessors(chain = true)
public class AppThirdAuth implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private Long ownerId;

    /**
     * app渠道:qdmetro-青岛地铁
     */
    private String channel;

    /**
     * 授权类型：01-地铁；02-公交; 03-地铁的用户信息授权
     */
    private String authType;

    /**
     * 授权id
     */
    private String authToken;

    /**
     * 授权状态0失效；1有效
     */
    private String status;

    /**
     * 授权日期
     */
    private Date authTime;

    /**
     * app名称
     */
    private String appName;

    /**
     * 授权内容
     */
    private String remark;

    private Date createTime;

    private Date updateTime;

    private String appChannel;
    private String userNo;

}
