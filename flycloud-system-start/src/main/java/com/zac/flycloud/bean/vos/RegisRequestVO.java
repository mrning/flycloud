package com.zac.flycloud.bean.vos;

import lombok.Data;

@Data
public class RegisRequestVO {
    private String phone;
    private String smscode;
    private String username;
    private String password;
    private String email;
}
