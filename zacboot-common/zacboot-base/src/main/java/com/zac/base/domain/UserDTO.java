package com.zac.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录用户信息
 * Created by macro on 2020/6/19.
 */
@Getter
@Setter
public class UserDTO {
    private Long id;
    private Integer status;
    private String clientId;
    private List<String> roles;

    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码加密后
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱地址
     */
    private String mail;

    /**
     * 电话
     */
    private String phone;

    private Long deptId;

    /**
     * 入职时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryDate;

}
