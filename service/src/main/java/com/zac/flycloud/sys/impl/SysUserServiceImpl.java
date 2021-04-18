package com.zac.flycloud.sys.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.base.SysBaseApiImpl;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.sys.SysDeptService;
import com.zac.flycloud.tablemodel.SysDept;
import com.zac.flycloud.tablemodel.SysUser;
import com.zac.flycloud.mapper.SysUserMapper;
import com.zac.flycloud.sys.SysUserService;
import com.zac.flycloud.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zac.flycloud.constant.CommonConstant.TOKEN_EXPIRE_TIME;

@Service("userService")
public class SysUserServiceImpl extends SysBaseApiImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysBaseAPI sysBaseAPI;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${flycloud.security.tokenKey}")
    private String tokenKey;

    /**
     * 根据用户名获取用户对象
     *
     * @param username
     * @return
     */
    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_CACHE, key = "#username")
    public SysUser getUserByName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        SysUser loginUser = new SysUser();
        SysUser sysUser = sysUserMapper.getUserByName(username);
        if (sysUser == null) {
            return null;
        }
        BeanUtils.copyProperties(sysUser, loginUser);
        return loginUser;
    }

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
        return DataResponseResult.success("密码重置成功!");
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
        return DataResponseResult.success("密码修改成功!");
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
            sysBaseAPI.addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_LOGIN_1, null);
            return result;
        }
        //情况2：根据用户信息查询，该用户已停用
        if (sysUser.getDeleted()) {
            sysBaseAPI.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已停用！", CommonConstant.LOG_TYPE_LOGIN_1, null);
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
    public JSONObject userInfo(SysUser sysUser) throws Exception {
        String username = sysUser.getUsername();
        // 登录信息记录到security
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成token
        String token = PasswordUtil.createToken(username, tokenKey);
        // 设置token缓存有效时间 1个小时
        redisUtil.set(token, username, TOKEN_EXPIRE_TIME);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token, TOKEN_EXPIRE_TIME);

        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDept> departs = sysDeptService.queryUserDeparts(sysUser.getUuid());
        obj.put("departs", departs);
        obj.put("token", token);
        obj.put("userInfo", sysUser);

        // 添加日志
        sysBaseAPI.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_LOGIN_1, null);
        return obj;
    }


}
