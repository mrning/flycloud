package com.zac.flycloud.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.mapper.SysUserDeptMapper;
import com.zac.flycloud.mapper.SysUserMapper;
import com.zac.flycloud.mapper.SysUserRoleMapper;
import com.zac.flycloud.sys.SysUserService;
import com.zac.flycloud.utils.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service("userService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
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
     * 数据库查询用户
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }


    @Override
    public SysUser getUserByEmail(String email) {
        return sysUserMapper.getUserByEmail(email);
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


}
