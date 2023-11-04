package com.zac.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zac.base.bizentity.SysUser;
import com.zac.base.bizentity.SysRole;

import java.util.List;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20
 * @Version:V1.0
 */
public interface SysBaseService<T> extends IService<T> {

    /**
     * 日志添加
     *
     * @param LogContent  内容
     * @param logType     日志类型(0:操作日志;1:登录日志;2:定时任务)
     * @param operatetype 操作类型(1:添加;2:修改;3:删除;)
     */
    void addLog(String LogContent, Integer logType, Integer operatetype);

    /**
     * 根据用户uuid查询用户信息
     *
     * @param uuid
     * @return
     */
    SysUser getUserByUuid(String uuid);

    /**
     * 通过用户账号查询角色集合
     *
     * @param username
     * @return
     */
    List<SysRole> getRolesByUsername(String username);

    /**
     * 获取所有有效用户 拼接查询条件
     *
     * @return
     */
    List<SysUser> queryAllUser(QueryWrapper wrapper);

}
