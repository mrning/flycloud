package com.zac.flycloud.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.entity.tablemodel.SysUserDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户账号查询用户信息
     * @param username
     * @return
     */
    SysUser getUserByName(@Param("username") String username);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    SysUser getUserByPhone(@Param("phone") String phone);


    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    SysUser getUserByEmail(@Param("email") String email);

    /**
     *  根据部门Id查询用户信息
     * @param page
     * @param departId
     * @return
     */
    IPage<SysUser> getUserByDepId(Page page, @Param("departId") String departId, @Param("username") String username);

    /**
     *  根据部门Ids,查询部门下用户信息
     * @param page
     * @param departIds
     * @return
     */
    IPage<SysUser> getUserByDepIds(Page page, @Param("departIds") List<String> departIds, @Param("username") String username);


    /**
     *  根据用户Ids,查询用户所属部门名称信息
     * @param userIds
     * @return
     */
    List<SysUserDept> getDeptNamesByUserIds(@Param("userIds")List<String> userIds);

    /**
     * 根据角色Id查询用户信息
     * @param page
     * @param
     * @return
     */
    IPage<SysUser> getUserByRoleId(Page page, @Param("roleId") String roleId, @Param("username") String username);

    /**
     * @Author zac
     * @Date 2019/12/13 16:10
     * @Description: 根据角色id批量删除角色与用户关系
     */
    void batchDeleteRoleUserRelation(@Param("roleIdArray") String[] roleIdArray);

    /**
     * @Author zac
     * @Date 2019/12/13 16:10
     * @Description: 根据角色id批量删除角色与权限关系
     */
    void batchDeleteRolePermissionRelation(@Param("roleIdArray") String[] roleIdArray);

    /**
     * 查询被逻辑删除的用户
     */
    List<SysUser> selectLogicDeleted(@Param(Constants.WRAPPER) Wrapper<SysUser> wrapper);

    /**
     * 还原被逻辑删除的用户
     */
    int revertLogicDeleted(@Param("userIds") String userIds, @Param("entity") SysUser entity);

    /**
     * 彻底删除被逻辑删除的用户
     */
    int deleteLogicDeleted(@Param("userIds") String userIds);


    /**
     *  根据部门Ids,查询部门下用户信息
     * @param departIds
     * @return
     */
    List<SysUser> queryByDepIds(@Param("departIds")List<String> departIds,@Param("username") String username);
}
