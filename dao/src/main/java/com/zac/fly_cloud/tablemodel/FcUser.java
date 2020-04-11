package com.zac.fly_cloud.tablemodel;

import com.zac.fly_cloud.actable.annotation.AutoColumn;
import lombok.Data;

import java.util.Date;

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
