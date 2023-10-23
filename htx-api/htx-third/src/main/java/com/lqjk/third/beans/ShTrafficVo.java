package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ShTrafficVo
 * @Description TODO
 * @Author YJD
 * @Date 2023/5/18 15:57
 * @Version 1.0
 */
@Data
public class ShTrafficVo implements Serializable {
    private String keyVersion;
    private String body;
    private String sign;
    private String token;
    private String appChannel;
}
