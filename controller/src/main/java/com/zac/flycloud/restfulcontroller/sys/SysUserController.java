package com.zac.flycloud.restfulcontroller.sys;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.sys.SysDeptService;
import com.zac.flycloud.sys.SysUserService;
import com.zac.flycloud.utils.PasswordUtil;
import com.zac.flycloud.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户注册接口
     *
     * @param jsonObject
     * @param user
     * @return
     */
    @PostMapping("/register")
    public DataResponseResult<JSONObject> userRegister(@RequestBody JSONObject jsonObject, SysUser user) {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        // 手机号
        String phone = jsonObject.getString("phone");
        // 用户输入的短信验证码
        String smscode = jsonObject.getString("smscode");
        // 后台实际发送的短信验证码
        Object code = redisUtil.get(phone);
        // 用户名
        String username = jsonObject.getString("username");
        // 密码
        String password = jsonObject.getString("password");
        // 邮箱
        String email = jsonObject.getString("email");

        //未设置用户名
        if(StringUtils.isBlank(username)){
            result.setMessage("用户名不能为空");
            result.setSuccess(false);
            return result;
        }
        //未设置密码
        if(StringUtils.isBlank(password)){
            result.setMessage("密码不能为空");
            result.setSuccess(false);
            return result;
        }
        SysUser sysUser1 = sysUserService.getUserByName(username);
        if (sysUser1 != null) {
            result.setMessage("用户名已注册");
            result.setSuccess(false);
            return result;
        }
        SysUser sysUser2 = sysUserService.getUserByPhone(phone);
        if (sysUser2 != null) {
            result.setMessage("该手机号已注册");
            result.setSuccess(false);
            return result;
        }
        if (!smscode.equals(code)) {
            result.setMessage("手机验证码错误");
            result.setSuccess(false);
            return result;
        }
        //校验邮箱
        if(StringUtils.isNotBlank(email)){
            SysUser sysUser3 = sysUserService.getUserByEmail(email);
            if (sysUser3 != null) {
                result.setMessage("邮箱已被注册");
                result.setSuccess(false);
                return result;
            }
        }

        try {
            user.setCreateTime(new Date());// 设置创建时间
            String passwordEncode = PasswordUtil.getPasswordEncode(password);
            user.setUsername(username);
            user.setRealname(username);
            user.setPassword(passwordEncode);
            user.setMail(email);
            user.setPhone(phone);
            DataResponseResult.ok("注册成功");
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }


    /**
	  *  校验用户账号是否唯一<br>
	  *  可以校验其他 需要检验什么就传什么。。。
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/checkOnlyUser", method = RequestMethod.GET)
    public DataResponseResult<Boolean> checkOnlyUser(SysUser sysUser) {
        DataResponseResult<Boolean> result = new DataResponseResult<>();
        //如果此参数为false则程序发生异常
        result.setResult(true);
        try {
            //通过传入信息查询新的用户信息
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>(sysUser));
            if (user != null) {
                result.setSuccess(false);
                result.setMessage("用户账号已存在");
                return result;
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    public DataResponseResult<?> changePassword(@RequestBody SysUser sysUser) {
        SysUser u = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()));
        if (u == null) {
            return DataResponseResult.error("用户不存在！");
        }
        sysUser.setId(u.getId());
        return sysUserService.changePassword(sysUser);
    }

	/**
	 * 首页用户重置密码
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public DataResponseResult<?> changPassword(@RequestBody JSONObject json) {
		String username = json.getString("username");
		String oldpassword = json.getString("oldpassword");
		String password = json.getString("password");
		String confirmpassword = json.getString("confirmpassword");
		SysUser user = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
		if(user==null) {
			return DataResponseResult.error("用户不存在！");
		}
		return sysUserService.resetPassword(username,oldpassword,password,confirmpassword);
	}
	
	/**
	 * 用户手机号验证
	 */
	@PostMapping("/phoneVerification")
	public DataResponseResult<String> phoneVerification(@RequestBody JSONObject jsonObject) {
        DataResponseResult<String> result = new DataResponseResult<String>();
		String phone = jsonObject.getString("phone");
		String smscode = jsonObject.getString("smscode");
		Object code = redisUtil.get(phone);
		if (!smscode.equals(code)) {
			result.setMessage("手机验证码错误");
			result.setSuccess(false);
			return result;
		}
		redisUtil.set(phone, smscode);
		result.setResult(smscode);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 用户更改密码
	 */
	@GetMapping("/passwordChange")
	public DataResponseResult<SysUser> passwordChange(@RequestParam(name="username")String username,
										  @RequestParam(name="password")String password,
			                              @RequestParam(name="smscode")String smscode,
			                              @RequestParam(name="phone") String phone) {
        DataResponseResult<SysUser> result = new DataResponseResult<SysUser>();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(smscode)  || StringUtils.isBlank(phone) ) {
            result.setMessage("重置密码失败！");
            result.setSuccess(false);
            return result;
        }

        SysUser sysUser=new SysUser();
        Object object= redisUtil.get(phone);
        if(null==object) {
        	result.setMessage("短信验证码失效！");
            result.setSuccess(false);
            return result;
        }
        if(!smscode.equals(object)) {
        	result.setMessage("短信验证码不匹配！");
            result.setSuccess(false);
            return result;
        }
        sysUser = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username).eq(SysUser::getPhone,phone));
        if (sysUser == null) {
            result.setMessage("未找到用户！");
            result.setSuccess(false);
            return result;
        } else {
            String passwordEncode = PasswordUtil.getPasswordEncode(password);
            sysUser.setPassword(passwordEncode);
            this.sysUserService.updateById(sysUser);
            result.setSuccess(true);
            result.setMessage("密码重置完成！");
            return result;
        }
    }

}
