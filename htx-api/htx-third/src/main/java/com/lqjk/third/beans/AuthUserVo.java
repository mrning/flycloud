package com.lqjk.third.beans;

import lombok.Data;

/**
 * @ClassName EmpowerVo
 * @Description 授权接受用户
 * @Author YJD
 * @Date 2023/4/6 14:43
 * @Version 1.0
 */
@Data
public class AuthUserVo {
    private String userToken;
    private String cardNo;
    private String resourceId;
    private String mobile;
    private String type;
}
