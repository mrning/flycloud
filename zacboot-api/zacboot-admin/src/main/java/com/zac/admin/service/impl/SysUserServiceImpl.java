package com.zac.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.db.Page;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.admin.beans.constants.AdminConstants;
import com.zac.admin.beans.vos.request.RegisRequest;
import com.zac.admin.beans.vos.request.UserAddRequest;
import com.zac.admin.beans.vos.request.UserUpdateRequest;
import com.zac.admin.beans.vos.response.SysUserResponse;
import com.zac.admin.dao.SysUserDao;
import com.zac.admin.mapper.SysUserMapper;
import com.zac.admin.service.*;
import com.zac.base.basebeans.PageResult;
import com.zac.base.basebeans.Result;
import com.zac.base.bizentity.*;
import com.zac.base.constants.CommonConstant;
import com.zac.base.constants.RedisKey;
import com.zac.base.enums.UserClientEnum;
import com.zac.base.utils.PasswordUtil;
import com.zac.base.utils.RedisUtil;
import com.zac.request.req.admin.UserRequest;
import com.zac.request.res.SysDeptResponse;
import com.zac.request.res.SysRoleResponse;
import com.zac.request.res.SysUserDeptAndRoleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AutoCreateFile
 *
 * @author zac
 * @date 2021年4月24日星期六
 */
@Slf4j
@Service
public class SysUserServiceImpl extends SysBaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysUserDeptService sysUserDeptService;

    @Autowired
    private RedisUtil redisUtil;

    public Integer add(UserAddRequest userAddRequest) {
        SysUser sysUser = SysUser.convertByRequest(userAddRequest);
        sysUser.setUuid(UUID.randomUUID().toString(Boolean.TRUE));
        if (!CollectionUtils.isEmpty(userAddRequest.getRoleUuids())) {
            sysUserRoleService.updateByUserUuid(sysUser.getUuid(), userAddRequest.getRoleUuids());
        }
        if (!CollectionUtils.isEmpty(userAddRequest.getDeptUuids())){
            sysUserDeptService.updateByUserUuid(sysUser.getUuid(), userAddRequest.getDeptUuids());
        }
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(PasswordUtil.getPasswordEncode(sysUser.getPassword()));
        }
        return sysUserDao.add(sysUser);
    }

    private static final String TOKEN_KEY = UserClientEnum.ADMIN.getValue() +":"+RedisKey.LOGIN_SYSTEM_USERINFO;

    public Integer del(SysUser sysUser) {
        Assert.isTrue(StringUtils.isNotBlank(sysUser.getUuid()), "参数异常，删除失败");
        Assert.isTrue(BeanUtil.isNotEmpty(sysUser), "不能全部属性为空");
        sysUserRoleService.delByUserUuid(sysUser.getUuid());
        sysUserDeptService.delByUserUuid(sysUser.getUuid());
        return sysUserDao.del(sysUser);
    }

    public Integer update(UserUpdateRequest userUpdateRequest, String token) {
        Assert.isTrue(StringUtils.isNotBlank(userUpdateRequest.getUuid()), "参数异常，更新失败");
        SysUser sysUser = SysUser.convertByRequest(userUpdateRequest);

        if (!CollectionUtils.isEmpty(userUpdateRequest.getRoleUuids())) {
            sysUserRoleService.updateByUserUuid(sysUser.getUuid(), userUpdateRequest.getRoleUuids());
        }
        if (!CollectionUtils.isEmpty(userUpdateRequest.getDeptUuids())){
            sysUserDeptService.updateByUserUuid(sysUser.getUuid(), userUpdateRequest.getDeptUuids());
        }

        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(PasswordUtil.getPasswordEncode(sysUser.getPassword()));
            SysUser sysUserCache = (SysUser) redisUtil.get(TOKEN_KEY + token);
            if (sysUserCache != null && sysUserCache.getUuid().equals(sysUser.getUuid())) {
                // 如果密码修改则用户退出重新登录
                logout(token);
            }
        }
        return sysUserDao.update(sysUser);
    }

    public PageResult<SysUserResponse> queryPage(UserRequest userRequest) {
        PageResult<SysUserResponse> pageResult = new PageResult<>();
        List<SysUserResponse> sysUsers = sysUserDao.queryPage(userRequest, new Page(userRequest.getPage(), userRequest.getPageSize()))
                .stream().map(sysUser -> {
                    SysUserResponse sysUserResponse = SysUserResponse.convertByEntity(sysUser);
                    sysUserResponse.setRoleUuids(sysUserRoleService.queryRolesByUserUuid(sysUser.getUuid()).stream().map(SysUserRole::getRoleUuid).collect(Collectors.toList()));
                    sysUserResponse.setDeptUuids(sysUserDeptService.queryDeptsByUserUuid(sysUser.getUuid()).stream().map(SysUserDept::getDeptUuid).collect(Collectors.toList()));
                    return sysUserResponse;
                }).collect(Collectors.toList());
        pageResult.setDataList(sysUsers);
        pageResult.setTotal(sysUserDao.queryPageCount(userRequest));
        return pageResult;
    }

    /**
     * 根据用户名获取用户对象
     *
     * @param username
     * @return
     */
    @Override
    @Cacheable(cacheNames = AdminConstants.SYS_USERS_CACHE, key = "#username")
    public SysUser getUserByName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return sysUserMapper.getUserByName(username);
    }

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param oldpassword     旧密码
     * @param newpassword     新密码
     * @param confirmpassword 确认新密码
     * @return
     */
    @Override
    @CacheEvict(value = {AdminConstants.SYS_USERS_CACHE}, allEntries = true)
    public Result<?> resetPassword(String username, String oldpassword, String newpassword, String confirmpassword) {
        SysUser user = sysUserMapper.getUserByName(username);
        String passwordEncode = PasswordUtil.getPasswordEncode(oldpassword);
        if (!user.getPassword().equals(passwordEncode)) {
            return Result.error("旧密码输入错误!");
        }
        if (StringUtils.isEmpty(newpassword)) {
            return Result.error("新密码不允许为空!");
        }
        if (!newpassword.equals(confirmpassword)) {
            return Result.error("两次输入密码不一致!");
        }
        String password = PasswordUtil.getPasswordEncode(newpassword);
        SysUser sysUser = new SysUser();
        sysUser.setPassword(password);
        this.sysUserMapper.update(sysUser, new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, user.getUuid()));
        return Result.success("密码重置成功!");
    }

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    @Override
    @CacheEvict(value = {AdminConstants.SYS_USERS_CACHE}, allEntries = true)
    public Result<?> changePassword(SysUser sysUser) {
        String password = sysUser.getPassword();
        String passwordEncode = PasswordUtil.getPasswordEncode(password);
        sysUser.setPassword(passwordEncode);
        this.sysUserMapper.updateById(sysUser);
        return Result.success("密码修改成功!");
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }


    @Override
    public SysUser getUserByEmail(String mail) {
        return sysUserMapper.getUserByEmail(mail);
    }

    /**
     * 校验用户是否有效
     *
     * @return
     */
    @Override
    public Result<JSONObject> checkUserIsEffective(SysUser sysUser){
        Result<JSONObject> result = new Result<>();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_LOGIN, null);
            result.error500("该用户不存在，请注册");
            return result;
        }
        //情况2：根据用户信息查询，该用户已停用
        if (sysUser.getDeleted()) {
            addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已停用！", CommonConstant.LOG_TYPE_LOGIN, null);
            result.error500("该用户已停用");
            return result;
        }
        return result;
    }

    /**
     * 用户信息
     *
     * @param sysUser
     * @return
     */
    @Override
    public JSONObject userInfo(SysUser sysUser) {
        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDept> departs = sysDeptService.queryUserDeparts(sysUser.getUuid());
        List<SysRole> roles = sysRoleService.getRolesByUsername(sysUser.getUsername());
        obj.put("departs", departs);
        obj.put("roles", roles);
        obj.put("userInfo", sysUser);

        // 添加日志
        addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_LOGIN, null);
        return obj;
    }

    @Override
    public boolean register(RegisRequest regisRequest) {
        try {
            UserAddRequest sysUser = new UserAddRequest();
            sysUser.setUsername(regisRequest.getUsername());
            sysUser.setRealName(regisRequest.getUsername());
            sysUser.setNickname(regisRequest.getUsername());
            sysUser.setPassword(PasswordUtil.getPasswordEncode(regisRequest.getPassword()));
            sysUser.setMail(regisRequest.getMail());
            sysUser.setPhone(regisRequest.getPhone());
            return add(sysUser) > 0;
        } catch (Exception e) {
            log.error("注册异常", e);
        }
        return false;
    }

    @Override
    public boolean logout(String token) {
        SysUser sysUser = (SysUser) redisUtil.get(TOKEN_KEY + token);
        if (sysUser == null) {
            return true;
        }
        redisUtil.del(TOKEN_KEY + token);
        return true;
    }

    @Override
    public SysUserDeptAndRoleInfo deptAndRoleInfo(UserRequest userRequest) {
        Assert.notNull(userRequest.getUserUuid(),"用户uuid不能为空");
        SysUser sysUser = sysUserDao.queryByUuid(userRequest.getUserUuid());
        if (null != sysUser){
            List<SysRole> roles = sysRoleService.queryUserRoles(userRequest.getUserUuid());
            List<SysDept> depts = sysDeptService.queryUserDeparts(userRequest.getUserUuid());

            SysUserDeptAndRoleInfo sysUserDeptAndRoleInfo = new SysUserDeptAndRoleInfo();
            sysUserDeptAndRoleInfo.setUserUuid(sysUser.getUuid());
            sysUserDeptAndRoleInfo.setUserName(sysUser.getRealName());
            sysUserDeptAndRoleInfo.setRoleResponseList(roles.stream().map(sysRole -> new SysRoleResponse().convertByEntity(sysRole)).toList());
            sysUserDeptAndRoleInfo.setDeptResponseList(depts.stream().map(sysDept -> new SysDeptResponse().convertByEntity(sysDept)).toList());

            return  sysUserDeptAndRoleInfo;
        }
        return null;
    }

    @Override
    public SysUser getInfo() {
        SysUser sysUser = sysUserDao.queryByUuid(String.valueOf(StpUtil.getLoginId()));

        return sysUser;
    }
}