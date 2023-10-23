package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserAuthCardNo
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/10 14:43
 * @Version 1.0
 */
@Data
public class UserAuthCardNo implements Serializable {
    private Long id;
    private Long ownerId;
    private String cardNo;
    private Long authId;
    private String userNo;
    private String channel;
}
