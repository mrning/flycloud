package com.zacboot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zacboot.system.core.entity.admin.SysDept;
import com.zacboot.system.core.entity.admin.SysUser;
import com.zacboot.system.core.entity.admin.SysUserDept;
import com.zacboot.admin.beans.dtos.TreeDto;
import com.zacboot.admin.beans.vos.request.DeptRequest;
import com.zacboot.admin.beans.vos.response.SysDeptResponse;
import com.zacboot.admin.dao.SysDeptDao;
import com.zacboot.admin.dao.SysUserDeptDao;
import com.zacboot.admin.mapper.SysDeptMapper;
import com.zacboot.admin.mapper.SysUserDeptMapper;
import com.zacboot.admin.service.SysDeptService;
import com.zacboot.admin.service.SysUserService;
import com.zacboot.admin.utils.FindsDeptsChildrenUtil;
import com.zacboot.common.base.basebeans.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2021年4月30日星期五
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends SysBaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;

    @Autowired
    private SysUserDeptDao sysUserDeptDao;

    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    @Autowired
    private SysUserService sysUserService;

    public Integer add(SysDept sysDept) {
        return sysDeptDao.add(sysDept);
    }

    public Integer del(SysDept sysDept) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysDept), "不能全部属性为空，会删除全表数据");
        return sysDeptDao.del(sysDept);
    }

    public Integer update(SysDept sysDept) {
        return sysDeptDao.update(sysDept);
    }

    public PageResult<SysDeptResponse> queryPage(DeptRequest deptRequest) {
        PageResult<SysDeptResponse> pageResult = new PageResult<>();
        List<SysDeptResponse> deptPageResponses = sysDeptDao.queryPage(deptRequest, new Page(deptRequest.getPageNumber(), deptRequest.getPageSize()))
                .stream().map(sysDept -> {
                    SysDeptResponse sysDeptResponse = SysDeptResponse.convertByEntity(sysDept);
                    // 上级部门名称
                    sysDeptResponse.setParentName(StringUtils.isNotBlank(sysDept.getParentUuid()) ? sysDeptDao.queryByUuid(sysDept.getParentUuid()).getDepartName() : "");
                    // 部门领导昵称
                    sysDeptResponse.setLeaderName(StringUtils.isNotBlank(sysDept.getLeaderUuid()) ? sysUserService.getUserByUuid(sysDept.getLeaderUuid()).getNickname() : "");
                    return sysDeptResponse;
                }).collect(Collectors.toList());
        pageResult.setDataList(deptPageResponses);
        pageResult.setTotal(sysDeptDao.queryPageCount(deptRequest).intValue());
        return pageResult;
    }

    @Override
    public List<SysDept> queryAll() {
        return sysDeptDao.queryAll();
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
        sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getDeptUuid, uuidList));
        return ok;
    }

    /**
     * delete 删除子部门
     *
     * @param uuids
     * @param subUuidList
     */
    private void checkChildrenExists(String[] uuids, List<String> subUuidList) {
        LambdaQueryWrapper<SysDept> query = new LambdaQueryWrapper<SysDept>();
        query.in(SysDept::getParentUuid, uuids);
        List<SysDept> departList = this.list(query);
        if (CollectionUtil.isNotEmpty(departList)) {
            subUuidList.addAll(departList.stream().map(SysDept::getUuid).collect(Collectors.toList()));
            // 递归获取子部门的子部门
            this.checkChildrenExists(subUuidList.toArray(new String[]{}), subUuidList);
        }
    }

    @Override
    public List<SysDept> queryUserDeparts(String userUuid) {
        return sysUserDeptDao.getDeptsByUserUuid(userUuid);
    }

    @Override
    public List<SysDept> queryDepartsByUsername(String username) {
        return queryUserDeparts(sysUserService.getUserByName(username).getUuid());
    }

    @Override
    public List<TreeDto> getDeptUsers() {
        List<TreeDto> allDepts = FindsDeptsChildrenUtil.wrapTreeDataToTreeList(queryAll());
        Map<String,List<TreeDto>> userTree = handleDeptUser(allDepts);
        handleDeptChild(allDepts, userTree);
        return allDepts;
    }

    private static void handleDeptChild(List<TreeDto> allDepts, Map<String, List<TreeDto>> userTree) {
        allDepts.forEach(dept -> {
            if(CollectionUtil.isEmpty(dept.getChildren())){
                dept.setChildren(userTree.get(dept.getKey()));
            }else{
                handleDeptChild(dept.getChildren(),userTree);
            }
        });
    }

    private Map<String,List<TreeDto>> handleDeptUser(List<TreeDto> allDepts){
        Map<String,List<TreeDto>> userTree = new HashMap<>();
        for (TreeDto dept : allDepts){
            if (CollectionUtil.isEmpty(dept.getChildren())){
                List<SysUser> users = sysUserDeptDao.getUsersByDeptUuid(dept.getKey());
                List<TreeDto> userTrees = users.stream().map(TreeDto::new).toList();
                userTree.put(dept.getKey(),userTrees);
            }else{
                return handleDeptUser(dept.getChildren());
            }
        }
        return userTree;
    }
}