package com.zac.flycloud.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.bean.dto.SysRole;
import com.zac.flycloud.bean.dto.SysUserRole;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	@Select("select * from sys_role where uuid in (select role_uuid from sys_user_role where user_uuid = (select uuid from sys_user where username=#{username}))")
	List<SysRole> getRoleByUserName(@Param("username") String username);

	@Select("select uuid from sys_role where uuid in (select role_uuid from sys_user_role where user_uuid = (select uuid from sys_user where username=#{username}))")
	List<String> getRoleIdByUserName(@Param("username") String username);

	@Select("select * from sys_role where uuid in (select role_uuid from sys_user_role where user_uuid = #{userUuid})")
	List<SysRole> getRolesByUserUuid(@Param("userUuid") String userUuid);

}
