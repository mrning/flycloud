package com.lqjk.third.beans;

import lombok.Data;

/**
 * @ClassName AuthTransType
 * @Description 授权类型
 * @Author YJD
 * @Date 2023/4/6 14:59
 * @Version 1.0
 */
@Data
public class AuthTransType {
    /**
     * 授权
     */
    public static final String AUTH="AUTH";
    /**
     * 解除授权
     */
    public static final String AUTH_CANCEL="AUTH_CANCEL";
    /**
     * 授权变更
     */
    public static final String AUTH_MODIFY="AUTH_MODIFY";
}
