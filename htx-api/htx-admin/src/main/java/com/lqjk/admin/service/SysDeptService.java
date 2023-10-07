package com.lqjk.admin.service;

import com.lqjk.base.bizentity.SysDept;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.admin.beans.dtos.TreeDto;
import com.lqjk.admin.beans.vos.request.DeptRequest;
import com.lqjk.request.res.SysDeptResponse;

import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
public interface SysDeptService extends SysBaseService<SysDept> {
    Integer add(SysDept SysDept);

    Integer del(SysDept SysDept);

    Integer update(SysDept SysDept);

    PageResult<SysDeptResponse> queryPage(DeptRequest deptRequest);

    List<SysDept> queryAll();


    /**
     * 根据一个或多个部门id删除，并删除其可能存在的子级部门
     * @param uuids
     * @return
     */
    boolean delete(String... uuids);

    /**
     * 根据用户id查询所属部门列表
     * @param userId
     * @return
     */
    List<SysDept> queryUserDeparts(String userId);

    /**
     * 根据用户名查询部门
     *
     * @param username
     * @return
     */
    List<SysDept> queryDepartsByUsername(String username);

    List<TreeDto> getDeptUsers();
}