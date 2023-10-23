package com.lqjk.third.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
@Data
@Accessors(chain = true)
public class CustomerTokenThird implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 三方token
     */
    private String token;

    /**
     * 三方来源
     */
    private String channel;

    /**
     * 过期时间
     */
    private String validityTime;

    /**
     * 设备号
     */
    private String customerEquipment;

    private Boolean disabled;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remark;

    /**
     * 用户id
     */
    private Long ownerId;

}
