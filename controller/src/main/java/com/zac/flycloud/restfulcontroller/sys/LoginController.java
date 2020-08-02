package com.zac.flycloud.restfulcontroller.sys;

import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.utils.*;
import com.zac.flycloud.base.SysBaseAPI;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.constant.CacheConstant;
import com.zac.flycloud.constant.CommonConstant;
import com.zac.flycloud.entity.SysUserLoginVO;
import com.zac.flycloud.entity.tablemodel.SysDept;
import com.zac.flycloud.entity.tablemodel.SysUser;
import com.zac.flycloud.log.SysLogService;
import com.zac.flycloud.sys.SysDeptService;
import com.zac.flycloud.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author zac
 * @Date 20200702
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "用户登录")
@Slf4j
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysBaseAPI sysBaseAPI;
    @Autowired
    private SysLogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public DataResponseResult<JSONObject> login(@RequestBody SysUserLoginVO sysLoginModel) {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        // 用户名
        String username = sysLoginModel.getUsername();
        // 密码
        String password = sysLoginModel.getPassword();
        // 验证码
        String captcha = sysLoginModel.getCaptcha();
        if (captcha == null) {
            result.error500("验证码无效");
            return result;
        }
        String lowerCaseCaptcha = captcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(lowerCaseCaptcha + sysLoginModel.getCheckKey(), "utf-8");
        Object checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.equals(lowerCaseCaptcha)) {
            result.error500("验证码错误");
            return result;
        }

        //1. 校验用户是否有效
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.getPasswordEncode(password);
        String syspassword = sysUser.getPassword();
        if (!PasswordUtil.getPasswordMatch(syspassword,userpassword)) {
            result.error500("用户名或密码错误");
            return result;
        }

        //用户登录信息
        userInfo(sysUser, result);
        sysBaseAPI.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

        return result;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public DataResponseResult<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader("X-Access-Token");
        if (StringUtils.isEmpty(token)) {
            return DataResponseResult.error("退出登录失败！");
        }
        String username = JwtUtil.getUsername(token);
        SysUser sysUser = sysBaseAPI.getUserByName(username);
        if (sysUser != null) {
            sysBaseAPI.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null);
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            return DataResponseResult.ok("退出登录成功！");
        } else {
            return DataResponseResult.error("Token无效!");
        }
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("loginfo")
    public DataResponseResult<JSONObject> loginfo() {
        DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
        JSONObject obj = new JSONObject();
        //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
        //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        obj.put("todayIp", todayIp);
        result.setResult(obj);
        DataResponseResult.success("登录成功", "ok");
        return result;
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("visitInfo")
    public DataResponseResult<List<Map<String, Object>>> visitInfo() {
        DataResponseResult<List<Map<String, Object>>> result = new DataResponseResult<List<Map<String, Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String, Object>> list = logService.findVisitCount(dayStart, dayEnd);
        result.setResult(ConverUtil.toLowerCasePageList(list));
        return result;
    }

    /**
     * 短信登录接口
     *
     * @param jsonObject
     * @return
     */
//	@PostMapping(value = "/sms")
//	public DataResponseResult<String> sms(@RequestBody JSONObject jsonObject) {
//		DataResponseResult<String> result = new DataResponseResult<String>();
//		String mobile = jsonObject.get("mobile").toString();
//		//手机号模式 登录模式: "2"  注册模式: "1"
//		String smsmode=jsonObject.get("smsmode").toString();
//		log.info(mobile);
//		if(StringUtils.isEmpty(mobile)){
//			result.setMessage("手机号不允许为空！");
//			result.setSuccess(false);
//			return result;
//		}
//		Object object = redisUtil.get(mobile);
//		if (object != null) {
//			result.setMessage("验证码10分钟内，仍然有效！");
//			result.setSuccess(false);
//			return result;
//		}
//
//		//随机数
//		String captcha = RandomUtil.randomNumbers(6);
//		JSONObject obj = new JSONObject();
//    	obj.put("code", captcha);
//		try {
//			boolean b = false;
//			//注册模板
//			if (CommonConstant.SMS_TPL_TYPE_1.equals(smsmode)) {
//				SysUser sysUser = userService.getUserByPhone(mobile);
//				if(sysUser!=null) {
//					result.error500(" 手机号已经注册，请直接登录！");
//					sysBaseAPI.addLog("手机号已经注册，请直接登录！", CommonConstant.LOG_TYPE_1, null);
//					return result;
//				}
//				b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.REGISTER_TEMPLATE_CODE);
//			}else {
//				//登录模式，校验用户有效性
//				SysUser sysUser = userService.getUserByPhone(mobile);
//				result = userService.checkUserIsEffective(sysUser);
//				if(!result.isSuccess()) {
//					return result;
//				}
//
//				/**
//				 * smsmode 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
//				 */
//				if (CommonConstant.SMS_TPL_TYPE_0.equals(smsmode)) {
//					//登录模板
//					b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.LOGIN_TEMPLATE_CODE);
//				} else if(CommonConstant.SMS_TPL_TYPE_2.equals(smsmode)) {
//					//忘记密码模板
//					b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
//				}
//			}
//
//			if (b == false) {
//				result.setMessage("短信验证码发送失败,请稍后重试");
//				result.setSuccess(false);
//				return result;
//			}
//			//验证码10分钟内有效
//			redisUtil.set(mobile, captcha, 600);
//			//update-begin--Author:scott  Date:20190812 for：issues#391
//			//result.setResult(captcha);
//			//update-end--Author:scott  Date:20190812 for：issues#391
//			result.setSuccess(true);
//
//		} catch (ClientException e) {
//			e.printStackTrace();
//			result.error500(" 短信接口未配置，请联系管理员！");
//			return result;
//		}
//		return result;
//	}


    /**
     * 手机号登录接口
     *
     * @param jsonObject
     * @return
     */
//	@ApiOperation("手机号登录接口")
//	@PostMapping("/phoneLogin")
//	public DataResponseResult<JSONObject> phoneLogin(@RequestBody JSONObject jsonObject) {
//		DataResponseResult<JSONObject> result = new DataResponseResult<JSONObject>();
//		String phone = jsonObject.getString("mobile");
//
//		//校验用户有效性
//		SysUser sysUser = userService.getUserByPhone(phone);
//		result = userService.checkUserIsEffective(sysUser);
//		if(!result.isSuccess()) {
//			return result;
//		}
//
//		String smscode = jsonObject.getString("captcha");
//		Object code = redisUtil.get(phone);
//		if (!smscode.equals(code)) {
//			result.setMessage("手机验证码错误");
//			return result;
//		}
//		//用户信息
//		userInfo(sysUser, result);
//		//添加日志
//		sysBaseAPI.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_1, null);
//
//		return result;
//	}


    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private DataResponseResult<JSONObject> userInfo(SysUser sysUser, DataResponseResult<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUsername();
        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDept> departs = sysDeptService.queryUserDeparts(sysUser.getUuid());
        obj.put("departs", departs);
        obj.put("token", token);
        obj.put("userInfo", sysUser);
        result.setResult(obj);
        DataResponseResult.ok("登录成功");
        return result;
    }

    /**
     * 后台生成图形验证码 ：有效
     *
     * @param response
     * @param key
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public DataResponseResult<String> randomImage(HttpServletResponse response, @PathVariable String key) {
        DataResponseResult<String> res = new DataResponseResult<String>();
        try {
            String code = MD5Util.createCode(4);

            log.debug("验证码 = " + code);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 60);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

}