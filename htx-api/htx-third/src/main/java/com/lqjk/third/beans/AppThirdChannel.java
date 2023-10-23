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
public class AppThirdChannel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 渠道号
     */
    private String channel;

    /**
     * 渠道名称
     */
    private String channelName;

    private Boolean disabled;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

}
