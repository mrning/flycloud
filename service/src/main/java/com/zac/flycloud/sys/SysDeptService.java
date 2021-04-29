package com.zac.flycloud.sys;

import com.zac.flycloud.base.SysBaseService;
import com.zac.flycloud.dto.TreeDto;
import com.zac.flycloud.tablemodel.SysDept;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * <p>
 * 
 * @Author:Steve
 * @Since：   2019-01-22
 */
public interface SysDeptService extends SysBaseService<SysDept> {

    /**
     * 查询所有部门信息,并分节点进行显示
     * @return
     */
    List<TreeDto> queryTreeList();


    /**
     * 更新depart数据
     * @param sysDepart
     * @return
     */
    Boolean updateDepartDataById(SysDept sysDepart,String username);

    
    /**
     * 根据关键字搜索相关的部门数据
     * @param keyWord
     * @return
     */
    List<TreeDto> searchBy(String keyWord, String myDeptSearch, String departIds);
    
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

    /**
     *  根据部门Id查询,当前和下级所有部门IDS
     * @param departId
     * @return
     */
    List<SysDept> getSubDepIdsByDepId(String departId);

    
}
