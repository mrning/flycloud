package com.zac.admin.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.base.basebeans.BaseEntity;
import com.zac.base.bizentity.SysUser;
import com.zac.base.bizentity.SysLog;
import com.zac.base.bizentity.SysRole;
import com.zac.admin.mapper.SysLogMapper;
import com.zac.admin.mapper.SysUserMapper;
import com.zac.admin.mapper.SysUserRoleMapper;
import com.zac.admin.service.SysBaseService;
import com.zac.base.utils.RedisUtil;
import com.zac.base.utils.SpringContextUtils;
import com.zac.base.utils.UrlIPUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20
 * @Version:V1.0
 */
@Slf4j
@Service
@Primary
public abstract class SysBaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements SysBaseService<T> {

    @Resource
    protected SysLogMapper sysLogMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    protected RedisUtil redisUtil;

    /**
     * 添加日志
     *
     * @param LogContent  内容
     * @param logType     日志类型(0:操作日志;1:登录日志;2:定时任务)
     * @param operatetype 操作类型(1:添加;2:修改;3:删除;)
     */
    @Override
    public void addLog(String LogContent, Integer logType, Integer operatetype) {
        SysLog sysLog = new SysLog();
        //注解上的描述,操作日志内容
        sysLog.setLogContent(LogContent);
        sysLog.setLogType(logType);
        sysLog.setOperateType(operatetype);

        //请求的方法名
        //请求的参数

        try {
            //获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            //设置IP地址
            sysLog.setIp(UrlIPUtil.getIpAddr(request));
        } catch (Exception e) {
            sysLog.setIp("127.0.0.1");
        }

        //获取登录用户信息
        sysLog.setUserid(null);
        sysLog.setUsername(null);
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setUuid(UUID.randomUUID().toString(true));
        sysLog.setDeleted(false);
        //保存系统日志
        sysLogMapper.insert(sysLog);
    }


    @Override
    public SysUser getUserByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        SysUser loginUser = new SysUser();
        SysUser sysUser = sysUserMapper.getUserByUuid(uuid);
        if (sysUser == null) {
            return null;
        }
        BeanUtils.copyProperties(sysUser, loginUser);
        return loginUser;
    }

    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return sysUserRoleMapper.getRoleByUserName(username);
    }


    @Override
    public List<SysUser> queryAllUser(QueryWrapper wrapper) {
        return sysUserMapper.selectList(wrapper);
    }

}