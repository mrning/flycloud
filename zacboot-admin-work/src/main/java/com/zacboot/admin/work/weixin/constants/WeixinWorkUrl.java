package com.zacboot.admin.work.weixin.constants;

public class WeixinWorkUrl {

    public static final String QW_HOST = "https://qyapi.weixin.qq.com";
    // 获取access_token 链接
    public static final String GET_TOKEN_URL = QW_HOST + "/cgi-bin/gettoken";
    // 获取部门id列表
    public static final String GET_DEPT_LIST_URL = QW_HOST + "/cgi-bin/department/simplelist";
    // 获取部门详情
    public static final String GET_DEPT_DETAIL_URL = QW_HOST + "/cgi-bin/department/get";

    public static final String GET_USER_LIST_BY_DEPT = QW_HOST + "/cgi-bin/user/simplelist";
}
