package com.zac.flycloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zac.flycloud.entity.tablemodel.SysUserDept;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SysUserDeptMapper extends BaseMapper<SysUserDept>{
	/**
	 * 根据用户id查询部门id
	 * @param userId
	 * @return
	 */
	List<SysUserDept> getUserDeptByUuid(@Param("userId") String userId);
}
