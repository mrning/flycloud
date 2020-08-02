package com.zac.flycloud.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.entity.tablemodel.SysPermission;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.entity.tablemodel.SysUserDept;
import com.zac.flycloud.entity.tablemodel.SysUserRole;
import com.zac.flycloud.mapper.SysPermissionMapper;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import com.zac.flycloud.mapper.SysUserMapper;
import com.zac.flycloud.mapper.SysUserRoleMapper;
import com.zac.flycloud.utils.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>  implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;
    @Autowired
    private SysBaseAPI sysBaseAPI;

    /**
     * 修改密码
     * @param username 用户名
     * @param oldpassword 旧密码
     * @param newpassword 新密码
     * @param confirmpassword 确认新密码
     * @return
     */
    @Override
    @CacheEvict(value = {CacheConstant.SYS_USERS_CACHE}, allEntries = true)
    public DataResponseResult<?> resetPassword(String username, String oldpassword, String newpassword, String confirmpassword) {
        SysUser user = sysUserMapper.getUserByName(username);
        String passwordEncode = PasswordUtil.getPasswordEncode(oldpassword);
        if (!user.getPassword().equals(passwordEncode)) {
            return DataResponseResult.error("旧密码输入错误!");
        }
        if (StringUtils.isEmpty(newpassword)) {
            return DataResponseResult.error("新密码不允许为空!");
        }
        if (!newpassword.equals(confirmpassword)) {
            return DataResponseResult.error("两次输入密码不一致!");
        }
        String password = PasswordUtil.getPasswordEncode(newpassword);
        SysUser sysUser = new SysUser();
        sysUser.setPassword(password);
        this.sysUserMapper.update(sysUser, new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, user.getUuid()));
        return DataResponseResult.ok("密码重置成功!");
    }

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @Override
    @CacheEvict(value = {CacheConstant.SYS_USERS_CACHE}, allEntries = true)
    public DataResponseResult<?> changePassword(SysUser sysUser) {
        String password = sysUser.getPassword();
        String passwordEncode = PasswordUtil.getPasswordEncode(password);
        sysUser.setPassword(passwordEncode);
        this.sysUserMapper.updateById(sysUser);
        return DataResponseResult.ok("密码修改成功!");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @Override
    @CacheEvict(value={CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String userId) {
        //1.删除用户
        this.removeById(userId);
        return false;
    }

    /**
     * 批量删除用户
     * @param userIds 逗号拼接的用户id
     * @return
     */
    @Override
    @CacheEvict(value={CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchUsers(String userIds) {
        //1.删除用户
        this.removeByIds(Arrays.asList(userIds.split(",")));
        return false;
    }

    /**
     * 数据库查询用户
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }

    /**
     * 给用户关联角色
     * @param user
     * @param roles
     */
    @Override
    @Transactional
    public void addUserWithRole(SysUser user, String roles) {
        this.save(user);
        if(StringUtils.isNotEmpty(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUserRole userRole = new SysUserRole(String.valueOf(user.getUuid()), roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    @CacheEvict(value= {CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    @Transactional
    public void editUserWithRole(SysUser user, String roles) {
        this.updateById(user);
        //先删后加
        sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserUuid, user.getUuid()));
        if(StringUtils.isNotEmpty(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUserRole userRole = new SysUserRole(user.getUuid(), roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }


    @Override
    public List<String> getRole(String username) {
        return sysUserRoleMapper.getRoleByUserName(username);
    }

    /**
     * 通过用户名获取用户角色集合
     * @param username 用户名
     * @return 角色集合
     */
    @Override
    public Set<String> getUserRolesSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = sysUserRoleMapper.getRoleByUserName(username);
        return new HashSet<>(roles);
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    @Override
    public Set<String> getUserPermissionsSet(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<SysPermission> permissionList = sysPermissionMapper.queryByUser(username);
        for (SysPermission po : permissionList) {
            if (StringUtils.isNotEmpty(po.getPerms())) {
                permissionSet.add(po.getPerms());
            }
        }
        return permissionSet;
    }

    // 根据部门Id查询
    @Override
    public IPage<SysUser> getUserByDepId(Page<SysUser> page, String departId,String username) {
        return sysUserMapper.getUserByDepId(page, departId,username);
    }

    @Override
    public IPage<SysUser> getUserByDepIds(Page<SysUser> page, List<String> departIds, String username) {
        return sysUserMapper.getUserByDepIds(page, departIds,username);
    }


    // 根据角色Id查询
    @Override
    public IPage<SysUser> getUserByRoleId(Page<SysUser> page, String roleId, String username) {
        return sysUserMapper.getUserByRoleId(page,roleId,username);
    }


    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }


    @Override
    public SysUser getUserByEmail(String email) {
        return sysUserMapper.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void addUserWithDepart(SysUser user, String selectedParts) {
        if(StringUtils.isNotEmpty(selectedParts)) {
            String[] arr = selectedParts.split(",");
            for (String deaprtId : arr) {
                SysUserDept userDeaprt = new SysUserDept(user.getUuid(), deaprtId);
                sysUserDeptMapper.insert(userDeaprt);
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value={CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    public void editUserWithDepart(SysUser user, String departs) {
        this.updateById(user);  //更新角色的时候已经更新了一次了，可以再跟新一次
        String[] arr = {};
        if(StringUtils.isNotEmpty(departs)){
            arr = departs.split(",");
        }
        //查询已关联部门
        List<SysUserDept> userDepartList = sysUserDeptMapper.selectList(new QueryWrapper<SysUserDept>().lambda().eq(SysUserDept::getUserUuid, user.getUuid()));
        //先删后加
        sysUserDeptMapper.delete(new QueryWrapper<SysUserDept>().lambda().eq(SysUserDept::getUserUuid, user.getUuid()));
        if(StringUtils.isNotEmpty(departs)) {
            for (String departId : arr) {
                SysUserDept userDepart = new SysUserDept(user.getUuid(), departId);
                sysUserDeptMapper.insert(userDepart);
            }
        }
    }


    /**
     * 校验用户是否有效
     * @param sysUser
     * @return
     */
    @Override
    public DataResponseResult<?> checkUserIsEffective(SysUser sysUser) {
        DataResponseResult<?> result = new DataResponseResult<Object>();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            result.error500("该用户不存在，请注册");
            sysBaseAPI.addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_1, null);
            return result;
        }
        //情况2：根据用户信息查询，该用户已停用
        if (!sysUser.getEnableStatus()) {
            sysBaseAPI.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已停用！", CommonConstant.LOG_TYPE_1, null);
            result.error500("该用户已停用");
            return result;
        }
        return result;
    }

    @Override
    public List<SysUser> queryLogicDeleted() {
        return this.queryLogicDeleted(null);
    }

    @Override
    public List<SysUser> queryLogicDeleted(LambdaQueryWrapper<SysUser> wrapper) {
        if (wrapper == null) {
            wrapper = new LambdaQueryWrapper<>();
        }
        wrapper.eq(SysUser::getEnableStatus, "1");
        return sysUserMapper.selectLogicDeleted(wrapper);
    }

    @Override
    public boolean revertLogicDeleted(List<String> userIds, SysUser updateEntity) {
        String ids = String.format("'%s'", String.join("','", userIds));
        return sysUserMapper.revertLogicDeleted(ids, updateEntity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeLogicDeleted(List<String> userIds) {
        String ids = String.format("'%s'", String.join("','", userIds));
        // 1. 删除用户
        int line = sysUserMapper.deleteLogicDeleted(ids);
        // 2. 删除用户部门关系
        line += sysUserDeptMapper.delete(new LambdaQueryWrapper<SysUserDept>().in(SysUserDept::getUserUuid, userIds));
        //3. 删除用户角色关系
        line += sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserUuid, userIds));
        return line != 0;
    }

    @Override
    public List<SysUser> queryByDepIds(List<String> departIds, String username) {
        return sysUserMapper.queryByDepIds(departIds,username);
    }

}
