package com.zac.flycloud.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zac.flycloud.entity.TreeDto;
import com.zac.flycloud.entity.tablemodel.SysDept;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * <p>
 * 
 * @Author:Steve
 * @Since：   2019-01-22
 */
public interface SysDeptService extends IService<SysDept>{

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
    List<TreeDto> searhBy(String keyWord,String myDeptSearch,String departIds);
    
    /**
     * 根据部门id删除并删除其可能存在的子级部门
     * @param id
     * @return
     */
    boolean delete(String id);
    
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
     * 根据部门id批量删除并删除其可能存在的子级部门
     * @param ids
     * @return
     */
	void deleteBatchWithChildren(List<String> ids);

    /**
     *  根据部门Id查询,当前和下级所有部门IDS
     * @param departId
     * @return
     */
    List<String> getSubDepIdsByDepId(String departId);

    
}
