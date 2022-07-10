package com.zac.flycloud.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Page;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zac.flycloud.admin.beans.constants.AdminConstants;
import com.zac.flycloud.admin.beans.entity.SysDept;
import com.zac.flycloud.admin.beans.entity.SysRole;
import com.zac.flycloud.admin.beans.entity.SysUser;
import com.zac.flycloud.admin.beans.vos.request.RegisRequest;
import com.zac.flycloud.admin.beans.vos.request.UserRequest;
import com.zac.flycloud.admin.dao.SysUserDao;
import com.zac.flycloud.admin.dao.mapper.SysUserMapper;
import com.zac.flycloud.admin.service.SysDeptService;
import com.zac.flycloud.admin.service.SysRoleService;
import com.zac.flycloud.admin.service.SysUserService;
import com.zac.flycloud.common.base.utils.PasswordUtil;
import com.zac.flycloud.common.basebeans.PageResult;
import com.zac.flycloud.common.basebeans.Result;
import com.zac.flycloud.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * AutoCreateFile
 * @date 2021年4月24日星期六
 * @author zac
 */
@Slf4j
@Service
public class SysUserServiceImpl extends SysBaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Value("${flycloud.security.tokenKey}")
    private String tokenKey;

    public Integer add(SysUser sysUser) {
        return sysUserDao.add(sysUser);
    }

    public Integer del(SysUser sysUser) {
        Assert.isTrue(BeanUtil.isNotEmpty(sysUser),"不能全部属性为空，会删除全表数据");
        return sysUserDao.del(sysUser);
    }

    public Integer update(SysUser sysUser) {
        return sysUserDao.update(sysUser);
    }

    public PageResult<SysUser> queryPage(UserRequest userRequest) {
        PageResult<SysUser> pageResult = new PageResult<>();
        pageResult.setDataList(sysUserDao.queryPage(userRequest,new Page(userRequest.getPageNumber(), userRequest.getPageSize())));
        pageResult.setTotal(sysUserDao.queryPageCount(userRequest).intValue());
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
     * @param username 用户名
     * @param oldpassword 旧密码
     * @param newpassword 新密码
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
    public SysUser getUserByEmail(String email) {
        return sysUserMapper.getUserByEmail(email);
    }


    /**
     * 校验用户是否有效
     * @param sysUser
     * @return
     */
    @Override
    public Result<?> checkUserIsEffective(SysUser sysUser) {
        Result<?> result = new Result<Object>();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_LOGIN_1, null);
            result.error500("该用户不存在，请注册");
            return result;
        }
        //情况2：根据用户信息查询，该用户已停用
        if (sysUser.getDeleted()) {
            addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已停用！", CommonConstant.LOG_TYPE_LOGIN_1, null);
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
    public JSONObject userInfo(SysUser sysUser) throws Exception {
        String username = sysUser.getUsername();
        // 登录信息记录到security
        UserDetails userDetails = reactiveUserDetailsService.findByUsername(username).block();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成token
        String token = PasswordUtil.createToken(username, tokenKey);
        // 设置token缓存有效时间 1个小时
        redisUtil.set(token, username, CommonConstant.TOKEN_EXPIRE_TIME);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token, CommonConstant.TOKEN_EXPIRE_TIME);

        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDept> departs = sysDeptService.queryUserDeparts(sysUser.getUuid());
        List<SysRole> roles =  sysRoleService.getRolesByUsername(sysUser.getUsername());
        obj.put("departs", departs);
        obj.put("roles", roles);
        obj.put("token", token);
        obj.put("userInfo", sysUser);

        // 添加日志
        addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_LOGIN_1, null);
        return obj;
    }

    @Override
    public boolean regis(RegisRequest regisRequest) {
        try {
            SysUser sysUser = new SysUser();
            sysUser.setCreateTime(new Date());// 设置创建时间
            sysUser.setUsername(regisRequest.getUsername());
            sysUser.setRealname(regisRequest.getUsername());
            sysUser.setPassword(PasswordUtil.getPasswordEncode(regisRequest.getPassword()));
            sysUser.setMail(regisRequest.getEmail());
            sysUser.setPhone(regisRequest.getPhone());
            return add(sysUser) > 0;
        } catch (Exception e) {
            log.error("注册异常",e);
        }
        return false;
    }
}