package com.zac.flycloud.start.bean.basebean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zac.flycloud.start.bean.annotation.AutoColumn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    @AutoColumn(isAutoIncrement = true,orderIndex = 0,type = "bigint")
    private Long id;

    @AutoColumn(isNull = false, orderIndex = 1)
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
    @AutoColumn(defaultValue = "0")
    private Boolean deleted;
}
