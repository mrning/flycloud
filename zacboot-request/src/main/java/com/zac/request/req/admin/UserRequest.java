package com.zac.request.req.admin;

import com.zac.base.basebeans.PageRequest;
import lombok.Data;

@Data
public class UserRequest extends PageRequest {
    private String userUuid;
    private String username;
    private String mail;
    private String phone;
    private String realName;
}
