package com.zacboot.core.request.admin;

import cn.hutool.db.Page;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserRequest extends Page {
    private String userUuid;
    private String username;
    private String mail;
    private String phone;
    private String realname;
}
