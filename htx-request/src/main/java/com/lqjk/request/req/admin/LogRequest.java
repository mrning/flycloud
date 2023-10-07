package com.lqjk.request.req.admin;

import cn.hutool.core.bean.BeanUtil;
import com.lqjk.base.bizentity.SysDept;
import com.lqjk.base.bizentity.SysLog;
import lombok.Data;

@Data
public class LogRequest {
    /**
     * 耗时
     */
    private Long costTime;

    /**
     * IP
     */
    private String ip;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 请求方法
     */
    private String method;

    /**
     * 操作人用户名称
     */
    private String username;
    /**
     * 操作人用户账户
     */
    private String userid;
    /**
     * 操作详细日志
     */
    private String logContent;

    /**
     * 日志类型（1登录日志，2操作日志）
     */
    private String logType;

    /**
     * 操作类型（1查询，2添加，3修改，4删除,5导入，6导出）
     */
    private Integer operateType;

    private String clientId;

    public LogRequest convertByEntity(SysLog sysLog) {
        if (null == sysLog){
            return null;
        }
        BeanUtil.copyProperties(sysLog,this);
        return this;
    }

    public SysLog convertToEntity(){
        SysLog sysLog = new SysLog();
        BeanUtil.copyProperties(this,sysLog);
        return sysLog;
    }
}
