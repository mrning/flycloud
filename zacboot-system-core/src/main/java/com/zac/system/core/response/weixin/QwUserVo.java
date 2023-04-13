package com.zac.system.core.response.weixin;

import lombok.Data;

@Data
public class QwUserVo {

    private Integer userId;

    private String name;

    private String mobile;

    private String email;

    private String address;

    private String avatar;
}
