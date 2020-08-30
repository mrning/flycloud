package com.zac.flycloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysRole;
import com.zac.flycloud.entity.tablemodel.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户mapper
 * 所有需要user表中数据作为查询条件的mapper都放这里
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

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

}
