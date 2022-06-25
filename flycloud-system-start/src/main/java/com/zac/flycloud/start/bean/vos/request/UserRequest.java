package com.zac.flycloud.start.bean.vos.request;

import cn.hutool.db.Page;
import lombok.Data;

@Data
public class UserRequest extends Page {
    private String userUuid;
    private String username;
    private String mail;
    private String phone;
    private String realname;
}
