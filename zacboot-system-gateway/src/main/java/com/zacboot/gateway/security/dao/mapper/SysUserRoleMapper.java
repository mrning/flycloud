package com.zacboot.gateway.security.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zacboot.gateway.security.bean.SysRole;
import com.zacboot.gateway.security.bean.SysUserRole;
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

	@Select("select * from sys_role where uuid in (select role_uuid from sys_user_role where user_uuid = #{userUuid})")
	List<SysRole> getRolesByUserUuid(@Param("userUuid") String userUuid);

}
