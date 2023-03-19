package com.zacboot.api.mini.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.system.core.entity.admin.SysLog;
import com.zac.system.core.entity.mini.MiniUserEntity;
import com.zacboot.api.mini.beans.dtos.MiniUserDto;
import com.zacboot.api.mini.mapper.MiniUserMapper;
import com.zacboot.api.mini.service.BaseService;
import com.zacboot.common.base.basebeans.BaseEntity;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.common.base.utils.SpringContextUtils;
import com.zacboot.common.base.utils.UrlIPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20
 * @Version:V1.0
 */
@Slf4j
@Service
@Primary
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    @Autowired
    private MiniUserMapper miniUserMapper;

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
            sysLog.setIp(UrlIPUtils.getIpAddr(request));
        } catch (Exception e) {
            sysLog.setIp("127.0.0.1");
        }

        //获取登录用户信息
        sysLog.setUserid(null);
        sysLog.setUsername(null);
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setUuid(UUID.randomUUID().toString(true));
        sysLog.setDeleted(false);
        log.info(sysLog.toString());
        //保存系统日志
//        sysLogMapper.insert(sysLog);
    }


    @Override
    public MiniUserDto getUserByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        MiniUserDto miniUserDto = new MiniUserDto();
        MiniUserEntity userEntity = miniUserMapper.selectOne(new LambdaQueryWrapper<MiniUserEntity>().eq(MiniUserEntity::getUuid,uuid));
        if (userEntity == null) {
            return null;
        }
        BeanUtils.copyProperties(userEntity, miniUserDto);
        return miniUserDto;
    }

}