package com.zacboot.api.mini.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlatformEnum {

    PLATFORM_PDD("pdd","拼多多"),
    PLATFORM_JD("jd","京东"),
    PLATFORM_TB("taobao","淘宝")
    ;

    private String name;
    private String desc;


}
