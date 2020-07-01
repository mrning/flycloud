package com.zac.flycloud.tablemodel;

import com.zac.flycloud.actable.annotation.AutoColumn;
import lombok.Data;

import java.util.Date;


/**
 * 用户表
 */
@Data
public class FcUser {

    private Long id;

    @AutoColumn(isNull = false)
    private String username;

    @AutoColumn(isNull = false)
    private String password;

    private String mail;

    private String nickname;

    private Date createTime;

    private Date updateTime;

    @AutoColumn(defaultValue = "1")
    private Boolean enableStatus;
}
