package com.zac.flycloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Page;
import com.zac.flycloud.basebean.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.flycloud.base.SysBaseServiceImpl;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.dao.SysDeptDao;
import com.zac.flycloud.dao.UserDeptDao;
import com.zac.flycloud.dto.SysDeptDTO;
import com.zac.flycloud.dto.TreeDto;
import com.zac.flycloud.mapper.SysDeptMapper;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import com.zac.flycloud.service.SysDeptService;
import com.zac.flycloud.service.SysUserService;
import com.zac.flycloud.sysutils.FindsDeptsChildrenUtil;
import com.zac.flycloud.tablemodel.SysDept;
import com.zac.flycloud.tablemodel.SysUserDept;
import com.zac.flycloud.vos.DeptRequestVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AutoCreateFile
 * @date 2021年4月30日星期五
 * @author zac
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends SysBaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;

    @Autowired
    private UserDeptDao userDeptDao;

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    @Autowired
    private SysUserService sysUserService;

    public Integer add(SysDeptDTO sysDeptDTO) {
        return sysDeptDao.add(sysDeptDTO);
    }

    public Integer del(SysDeptDTO sysDeptDTO) {
        Assert.isTrue(BeanUtil.isEmpty(sysDeptDTO),"不能全部属性为空，会删除全表数据");
        return sysDeptDao.del(sysDeptDTO);
    }

    public Integer update(SysDeptDTO sysDeptDTO) {
        return sysDeptDao.update(sysDeptDTO);
    }

    public PageResult<SysDeptDTO> queryPage(DeptRequestVO deptRequestVO) {
        PageResult<SysDeptDTO> pageResult = new PageResult<>();
        pageResult.setDataList(sysDeptDao.queryPage(deptRequestVO,new Page(deptRequestVO.getPageNumber(),deptRequestVO.getPageSize())));
        pageResult.setTotal(sysDeptDao.queryPageCount(deptRequestVO).intValue());
        return pageResult;
    }

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

    /**
     * 根据部門id获取子部门
     * @param departId
     * @return
     */
    @Override
    public List<SysDept> getSubDepIdsByDepId(String departId) {
        return list(new LambdaQueryWrapper<SysDept>().eq(SysDept::getParentId, departId));
    }

    /**
     * <p>
     * 根据关键字搜索相关的部门数据
     * </p>
     */
    @Override
    public List<TreeDto> searchBy(String keyWord, String myDeptSearch, String departIds) {
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
    public boolean delete(String... uuids) {
        List<String> uuidList = new ArrayList<>();
        uuidList.addAll(Arrays.asList(uuids));
        // 检查是否存在子部门
        this.checkChildrenExists(uuids, uuidList);

        boolean ok = this.removeByIds(uuidList);
        //根据部门id删除用户与部门关系
        sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getDeptUuid,uuidList));
        return ok;
    }

    /**
     * delete 删除子部门
     * @param uuids
     * @param subUuidList
     */
    private void checkChildrenExists(String[] uuids, List<String> subUuidList) {
        LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<SysDept>();
        query.in(SysDept::getParentId,uuids);
        List<SysDept> departList = this.list(query);
        if(CollectionUtil.isNotEmpty(departList)) {
            subUuidList.addAll(departList.stream().map(SysDept::getUuid).collect(Collectors.toList()));
            // 递归获取子部门的子部门
            this.checkChildrenExists(subUuidList.toArray(new String[]{}), subUuidList);
        }
    }

    @Override
    public List<SysDept> queryUserDeparts(String userUuid) {
        return userDeptDao.getDeptsByUserUuid(userUuid);
    }

    @Override
    public List<SysDept> queryDepartsByUsername(String username) {
        return queryUserDeparts(sysUserService.getUserByName(username).getUuid());
    }
}