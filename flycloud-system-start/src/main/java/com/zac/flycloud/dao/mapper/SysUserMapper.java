package com.zac.flycloud.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.bean.tablemodel.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("select * from sys_user where username = #{username} and deleted = 0")
    SysUser getUserByName(@Param("username") String username);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    @Select("select * from sys_user where phone = #{phone} and deleted = 0")
    SysUser getUserByPhone(@Param("phone") String phone);

    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    @Select("select * from sys_user where email = #{email} and deleted = 0")
    SysUser getUserByEmail(@Param("email") String email);

}
