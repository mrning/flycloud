package com.zac.flycloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysUserDept;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysUserDeptMapper extends BaseMapper<SysUserDept>{

	/**
	 * 根据用户uuid获取所属部门列表
	 * @param userUuid
	 * @return
	 */
	@Select("select * from sys_dept	where uuid in (select dept_uuid from sys_user_dept where user_uuid = #{userUuid})")
	List<SysDept> getDeptsByUserUuid(@org.apache.ibatis.annotations.Param("userUuid") String userUuid);

	/**
	 * 根据用户id查询部门id
	 * @param userId
	 * @return
	 */
	List<SysUserDept> getUserDeptByUuid(@Param("userId") String userId);
}
