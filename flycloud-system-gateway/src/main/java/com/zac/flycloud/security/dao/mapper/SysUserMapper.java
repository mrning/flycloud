package com.zac.flycloud.security.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.security.bean.SysUser;
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

}
