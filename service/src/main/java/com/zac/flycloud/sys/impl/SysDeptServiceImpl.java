package com.zac.flycloud.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.dto.TreeDto;
import com.zac.flycloud.mapper.SysDeptMapper;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import com.zac.flycloud.tablemodel.SysDept;
import com.zac.flycloud.tablemodel.SysUserDept;
import com.zac.flycloud.sys.SysDeptService;
import com.zac.flycloud.sys.sysutils.FindsDeptsChildrenUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 部门表 服务实现类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	@Autowired
	private SysUserDeptMapper sysUserDeptMapper;
	
	/**
	 * queryTreeList 对应 queryTreeList 查询所有的部门数据,以树结构形式响应给前端
	 */
	@Cacheable(value = CacheConstant.SYS_DEPARTS_CACHE)
	@Override
	public List<TreeDto> queryTreeList() {
		LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<SysDept>();
		query.eq(SysDept::getDeleted, CommonConstant.DEL_FLAG_0.toString());
		List<SysDept> list = this.list(query);
		// 调用wrapTreeDataToTreeList方法生成树状数据
		List<TreeDto> listResult = FindsDeptsChildrenUtil.wrapTreeDataToTreeList(list);
		return listResult;
	}

	/**
	 * updateDepartDataById 对应 edit 根据部门主键来更新对应的部门数据
	 */
	@Override
	@Transactional
	public Boolean updateDepartDataById(SysDept SysDept, String username) {
		if (SysDept != null && username != null) {
			SysDept.setUpdateTime(new Date());
			SysDept.setUpdateUser(username);
			this.updateById(SysDept);
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatchWithChildren(List<String> ids) {
		List<String> idList = new ArrayList<String>();
		for(String id: ids) {
			idList.add(id);
			this.checkChildrenExists(id, idList);
		}
		this.removeByIds(idList);
		
		//根据部门id删除用户与部门关系
		sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getDeptUuid,idList));
		
	}

	/**
	 * 根據部門id獲取子部門列表
	 * @param departId
	 * @return
	 */
	@Override
	public List<String> getSubDepIdsByDepId(String departId) {
		return this.baseMapper.getSubDepIdsByDepId(departId);
	}

	/**
	 * <p>
	 * 根据关键字搜索相关的部门数据
	 * </p>
	 */
	@Override
	public List<TreeDto> searhBy(String keyWord, String myDeptSearch, String departIds) {
		LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<SysDept>();
		List<TreeDto> newList = new ArrayList<>();
		//myDeptSearch不为空时为我的部门搜索，只搜索所负责部门
		if(!StringUtil.isNullOrEmpty(myDeptSearch)){
			//departIds 为空普通用户或没有管理部门
			if(StringUtil.isNullOrEmpty(departIds)){
				return newList;
			}
			query.eq(SysDept::getDeleted, CommonConstant.DEL_FLAG_0.toString());
		}
		query.like(SysDept::getDepartName, keyWord);
		TreeDto model = new TreeDto();
		List<SysDept> departList = this.list(query);
		if(departList.size() > 0) {
			for(SysDept depart : departList) {
				model = new TreeDto(depart);
				model.setChildren(null);
				newList.add(model);
			}
			return newList;
		}
		return null;
	}

	/**
	 * 根据部门id删除并且删除其可能存在的子级任何部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(String id) {
		List<String> idList = new ArrayList<>();
		idList.add(id);
		this.checkChildrenExists(id, idList);
		//清空部门树内存
		//FindsDeptsChildrenUtil.clearDepartIdModel();
		boolean ok = this.removeByIds(idList);
		//根据部门id获取部门角色id
		List<String> roleIdList = new ArrayList<>();
		//根据部门id删除用户与部门关系
		sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getDeptUuid,idList));
		return ok;
	}
	
	/**
	 * delete 方法调用
	 * @param id
	 * @param idList
	 */
	private void checkChildrenExists(String id, List<String> idList) {	
		LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<SysDept>();
		query.eq(SysDept::getParentId,id);
		List<SysDept> departList = this.list(query);
		if(departList != null && departList.size() > 0) {
			for(SysDept sysDept : departList) {
				idList.add(sysDept.getUuid());
				this.checkChildrenExists(sysDept.getUuid(), idList);
			}
		}
	}

	@Override
	public List<SysDept> queryUserDeparts(String userUuid) {
		return baseMapper.queryUserDeparts(userUuid);
	}

	@Override
	public List<SysDept> queryDepartsByUsername(String username) {
		return baseMapper.queryDepartsByUsername(username);
	}

}
