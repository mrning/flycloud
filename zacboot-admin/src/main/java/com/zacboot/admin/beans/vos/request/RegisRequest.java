package com.zacboot.admin.beans.vos.request;

import lombok.Data;

@Data
public class RegisRequest {
    private String phone;
    private String smscode;
    private String username;
    private String password;
    private String email;
}
