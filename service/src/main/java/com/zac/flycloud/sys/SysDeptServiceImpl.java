package com.zac.flycloud.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.dto.TreeDto;
import com.zac.flycloud.mapper.SysDeptMapper;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import com.zac.flycloud.tablemodel.SysDept;
import com.zac.flycloud.tablemodel.SysUserDept;
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
		query.eq(SysDept::getDelFlag, CommonConstant.DEL_FLAG_0.toString());
		List<SysDept> list = this.list(query);
		// 调用wrapTreeDataToTreeList方法生成树状数据
		List<TreeDto> listResult = FindsDeptsChildrenUtil.wrapTreeDataToTreeList(list);
		return listResult;
	}


	/**
	 * saveDepartData 对应 add 保存用户在页面添加的新的部门对象数据
	 */
	@Override
	@Transactional
	public void saveDepartData(SysDept sysDept, String username) {
		if (sysDept != null && username != null) {
			if (sysDept.getParentId() == null) {
				sysDept.setParentId("");
			}
			String s = UUID.randomUUID().toString().replace("-", "");
			sysDept.setId(s);
			// 先判断该对象有无父级ID,有则意味着不是最高级,否则意味着是最高级
			// 获取父级ID
			String parentId = sysDept.getParentId();
			JSONObject formData = new JSONObject();
			formData.put("parentId",parentId);
			sysDept.setCreateTime(new Date());
			sysDept.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
			this.save(sysDept);
		}

	}
	
	/**
	 * saveDepartData 的调用方法,生成部门编码和部门类型（作废逻辑）
	 * @deprecated
	 * @param parentId
	 * @return
	 */
	private String[] generateOrgCode(String parentId) {	
		//update-begin--Author:Steve  Date:20190201 for：组织机构添加数据代码调整
				LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<SysDept>();
				LambdaQueryWrapper<SysDept> query1 = new LambdaQueryWrapper<SysDept>();
				String[] strArray = new String[2];
		        // 创建一个List集合,存储查询返回的所有SysDept对象
		        List<SysDept> departList = new ArrayList<>();
				// 定义新编码字符串
				String newOrgCode = "";
				// 定义旧编码字符串
				String oldOrgCode = "";
				// 定义部门类型
				String orgType = "";
				// 如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
				if (StringUtil.isNullOrEmpty(parentId)) {
					// 线判断数据库中的表是否为空,空则直接返回初始编码
					query1.eq(SysDept::getParentId, "").or().isNull(SysDept::getParentId);
					query1.orderByDesc(SysDept::getOrgCode);
					departList = this.list(query1);
					if(departList == null || departList.size() == 0) {
						strArray[0] = YouBianCodeUtil.getNextYouBianCode(null);
						strArray[1] = "1";
						return strArray;
					}else {
					SysDept depart = departList.get(0);
					oldOrgCode = depart.getOrgCode();
					orgType = depart.getOrgType();
					newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
					}
				} else { // 反之则查询出所有同级的部门,获取结果后有两种情况,有同级和没有同级
					// 封装查询同级的条件
					query.eq(SysDept::getParentId, parentId);
					// 降序排序
					query.orderByDesc(SysDept::getOrgCode);
					// 查询出同级部门的集合
					List<SysDept> parentList = this.list(query);
					// 查询出父级部门
					SysDept depart = this.getById(parentId);
					// 获取父级部门的Code
					String parentCode = depart.getOrgCode();
					// 根据父级部门类型算出当前部门的类型
					orgType = String.valueOf(Integer.valueOf(depart.getOrgType()) + 1);
					// 处理同级部门为null的情况
					if (parentList == null || parentList.size() == 0) {
						// 直接生成当前的部门编码并返回
						newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, null);
					} else { //处理有同级部门的情况
						// 获取同级部门的编码,利用工具类
						String subCode = parentList.get(0).getOrgCode();
						// 返回生成的当前部门编码
						newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, subCode);
					}
				}
				// 返回最终封装了部门编码和部门类型的数组
				strArray[0] = newOrgCode;
				strArray[1] = orgType;
				return strArray;
		//update-end--Author:Steve  Date:20190201 for：组织机构添加数据代码调整
	} 
	

	/**
	 * updateDepartDataById 对应 edit 根据部门主键来更新对应的部门数据
	 */
	@Override
	@Transactional
	public Boolean updateDepartDataById(SysDept SysDept, String username) {
		if (SysDept != null && username != null) {
			SysDept.setUpdateTime(new Date());
			SysDept.setUpdateBy(username);
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
		sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getDepId,idList));
		
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

	@Override
	public List<String> getMySubDepIdsByDepId(String departIds) {
		//根据部门id获取所负责部门
		String[] codeArr = this.getMyDeptParentOrgCode(departIds);
		return this.baseMapper.getSubDepIdsByOrgCodes(codeArr);
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
			//根据部门id获取所负责部门
			String[] codeArr = this.getMyDeptParentOrgCode(departIds);
			for(int i=0;i<codeArr.length;i++){
				query.or().likeRight(SysDept::getOrgCode,codeArr[i]);
			}
			query.eq(SysDept::getDelFlag, CommonConstant.DEL_FLAG_0.toString());
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
		sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getDepId,idList));
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
			for(SysDept depart : departList) {
				idList.add(depart.getId());
				this.checkChildrenExists(depart.getId(), idList);
			}
		}
	}

	@Override
	public List<SysDept> queryUserDeparts(String userId) {
		return baseMapper.queryUserDeparts(userId);
	}

	@Override
	public List<SysDept> queryDepartsByUsername(String username) {
		return baseMapper.queryDepartsByUsername(username);
	}
	

	/**
	 * 获取同一公司中部门编码长度最小的部门
	 * @param str
	 * @return
	 */
	private String getMinLengthNode(String[] str){
		int min =str[0].length();
		String orgCode = str[0];
		for(int i =1;i<str.length;i++){
			if(str[i].length()<=min){
				min = str[i].length();
				orgCode = orgCode+","+str[i];
			}
		}
		return orgCode;
	}
}
