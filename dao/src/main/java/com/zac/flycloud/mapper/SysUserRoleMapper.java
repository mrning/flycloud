package com.zac.flycloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.tablemodel.SysRole;
import com.zac.flycloud.tablemodel.SysUser;
import com.zac.flycloud.tablemodel.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @Author zac
 * @since 2018-12-21
 */
public interface SysUserRoleMapper extends BaseMapper<SysUser>{

	@Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
	List<String> getRoleByUserName(@Param("username") String username);

	@Select("select id from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
	List<String> getRoleIdByUserName(@Param("username") String username);

	@Select("select * from sys_role where uuid in (select role_uuid from sys_user_role where user_uuid = #{userUuid})")
	List<SysRole> getRolesByUserUuid(@Param("userUuid") String userUuid);

}
