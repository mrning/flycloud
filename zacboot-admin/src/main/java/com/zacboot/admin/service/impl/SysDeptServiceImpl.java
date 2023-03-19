package com.zacboot.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.system.core.entity.admin.SysDept;
import com.zac.system.core.entity.admin.SysUserDept;
import com.zacboot.admin.beans.vos.request.DeptRequest;
import com.zacboot.admin.beans.vos.response.SysDeptPageResponse;
import com.zacboot.admin.dao.SysDeptDao;
import com.zacboot.admin.dao.SysUserDeptDao;
import com.zacboot.admin.mapper.SysDeptMapper;
import com.zacboot.admin.mapper.SysUserDeptMapper;
import com.zacboot.admin.service.SysDeptService;
import com.zacboot.admin.service.SysUserService;
import com.zacboot.common.base.basebeans.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        sysDept.setUuid(UUID.randomUUID().toString(true));
        return sysDeptDao.add(sysDept);
    }

    public Integer del(SysDept sysDept) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysDept), "不能全部属性为空，会删除全表数据");
        return sysDeptDao.del(sysDept);
    }

    public Integer update(SysDept sysDept) {
        return sysDeptDao.update(sysDept);
    }

    public PageResult<SysDeptPageResponse> queryPage(DeptRequest deptRequest) {
        PageResult<SysDeptPageResponse> pageResult = new PageResult<>();
        List<SysDeptPageResponse> deptPageResponses = sysDeptDao.queryPage(deptRequest, new Page(deptRequest.getPageNumber(), deptRequest.getPageSize()))
                .stream().map(sysDept -> {
                    SysDeptPageResponse sysDeptPageResponse = SysDeptPageResponse.convertByEntity(sysDept);
                    // 上级部门名称
                    sysDeptPageResponse.setParentName(StringUtils.isNotBlank(sysDept.getParentUuid()) ? sysDeptDao.queryByUuid(sysDept.getParentUuid()).getDepartName() : "");
                    // 部门领导昵称
                    sysDeptPageResponse.setLeaderName(StringUtils.isNotBlank(sysDept.getLeaderUuid()) ? sysUserService.getUserByUuid(sysDept.getLeaderUuid()).getNickname() : "");
                    return sysDeptPageResponse;
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
}