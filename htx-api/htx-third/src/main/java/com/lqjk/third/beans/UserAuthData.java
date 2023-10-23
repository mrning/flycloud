package com.lqjk.third.beans;

import lombok.Data;

/**
 * @Description TODO
 * @Author YJD
 * @Date 2023/5/22 17:03
 * @Version 1.0
 */
@Data
public class UserAuthData {
    private String userToken;
    private String transTime;
    private String mobile;
    private String status;
}
