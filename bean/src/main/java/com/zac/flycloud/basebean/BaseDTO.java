package com.zac.flycloud.basebean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BaseDTO {
    private static final long serialVersionUID = 11L;

    private Long id;

    private String uuid;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除，0未删除，1已删除
     */
    private Boolean deleted;

    /**
     * 当前页数，从0开始
     */
    private int pageNumber = 0;

    /**
     * 每页条数
     */
    private int pageSize = 10;
}
