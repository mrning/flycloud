package com.zacboot.auth.service.impl;

import cn.hutool.core.lang.UUID;
import com.zacboot.auth.beans.domain.SysLog;
import com.zacboot.common.base.utils.SpringContextUtils;
import com.zacboot.common.base.utils.UrlIPUtils;
import com.zacboot.auth.mapper.SysLogMapper;
import com.zacboot.auth.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void addLog(String LogContent, Integer logType, Integer operateType) {
        SysLog sysLog = new SysLog();
        //注解上的描述,操作日志内容
        sysLog.setLogContent(LogContent);
        sysLog.setLogType(logType);
        sysLog.setOperateType(operateType);

        try {
            //获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            //设置IP地址
            sysLog.setIp(UrlIPUtils.getIpAddr(request));
        } catch (Exception e) {
            sysLog.setIp("127.0.0.1");
        }

        //获取登录用户信息 TODO
        sysLog.setUserid(null);
        sysLog.setUsername(null);

        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setUuid(UUID.randomUUID().toString(true));
        sysLog.setDeleted(false);
        //保存系统日志
        sysLogMapper.insert(sysLog);
    }
}
