package com.zac.flycloud.base;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zac.flycloud.tablemodel.SysDept;
import com.zac.flycloud.tablemodel.SysRole;
import com.zac.flycloud.tablemodel.SysUser;

import java.util.List;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20
 * @Version:V1.0
 */
public interface SysBaseAPI<T> extends IService<T> {

    /**
     * 日志添加
     *
     * @param LogContent  内容
     * @param logType     日志类型(0:操作日志;1:登录日志;2:定时任务)
     * @param operatetype 操作类型(1:添加;2:修改;3:删除;)
     */
    void addLog(String LogContent, Integer logType, Integer operatetype);

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    SysUser getUserById(String id);

    /**
     * 通过用户账号查询角色集合
     *
     * @param username
     * @return
     */
    List<String> getRolesByUsername(String username);

    /**
     * 通过用户账号查询部门 name
     *
     * @param username
     * @return 部门 name
     */
    List<String> getDepartNamesByUsername(String username);

    /**
     * 查询所有部门，拼接查询条件
     *
     * @return
     */
    List<JSONObject> queryAllDepart(Wrapper wrapper);

    /**
     * 获取所有有效用户 拼接查询条件
     *
     * @return
     */
    List<JSONObject> queryAllUser(Wrapper wrapper);

    /**
     * 获取所有角色
     *
     * @return
     */
    List<SysRole> queryAllRole();

    /**
     * 获取所有角色 带参
     * roleIds 默认选中角色
     *
     * @return
     */
    List<SysRole> queryAllRole(String[] roleIds);

    /**
     * 通过用户账号查询角色Id集合
     *
     * @param username
     * @return
     */
    List<String> getRoleIdsByUsername(String username);

    /**
     * 通过部门编号查询部门id
     *
     * @param orgCode
     * @return
     */
    String getDepartIdsByOrgCode(String orgCode);

    /**
     * 查询上一级部门
     *
     * @param departId
     * @return
     */
    SysDept getParentDepartId(String departId);

    /**
     * 查询所有部门
     *
     * @return
     */
    List<SysDept> getAllSysDept();

    /**
     * 根据部门Id获取部门负责人
     *
     * @param deptId
     * @return
     */
    List<String> getDeptHeadByDepId(String deptId);

}
