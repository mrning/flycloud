package com.zac.flycloud.vos;

import cn.hutool.db.Page;
import lombok.Data;

@Data
public class UserRequestVO extends Page {
    private String userUuid;
    private String username;
    private String mail;
    private String phone;
    private String realname;
}
