package com.lqjk.third.beans;

import lombok.Data;

import java.util.List;

/**
 * @ClassName EmpowerVo
 * @Description 上海授权功能接受参数
 * @Author YJD
 * @Date 2023/4/6 14:43
 * @Version 1.0
 */
@Data
public class AuthDataVo {
    private String authNo;
    private String transTime;
    private String transType;
    private List<AuthUserVo> authList;
}
