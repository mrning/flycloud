package com.lqjk.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.lqjk.admin.entity.SysLog;
import com.lqjk.admin.beans.vos.request.SysLogRequest;
import com.lqjk.admin.service.SysLogService;
import com.lqjk.base.basebeans.PageResult;
import com.lqjk.base.basebeans.Result;
import com.lqjk.base.utils.ConverUtil;
import com.lqjk.base.utils.DateUtil;
import com.lqjk.request.req.admin.LogRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * AutoCreateFile 日志相关 
 * @date 2021年4月24日星期六
 * @author zac
 */
@RestController
@RequestMapping("/sysLog")
@Slf4j
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AdminSysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * AutoCreateFile add
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody LogRequest sysLog) {
        return Result.success(sysLogService.add(sysLog));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/del")
    public Result<Integer> del(@RequestBody SysLog sysLog) {
        return Result.success(sysLogService.del(sysLog));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody SysLog sysLog) {
        return Result.success(sysLogService.update(sysLog));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/queryPage")
    public Result<PageResult<SysLog>> queryPage(@RequestBody SysLogRequest sysLogRequest) {
        return Result.success(sysLogService.queryPage(sysLogRequest));
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("loginfo")
    public Result<Object> loginfo() {
        JSONObject obj = new JSONObject();
        // 获取一天的开始和结束时间
        Date dayStart = DateUtil.getTodayStart();
        Date dayEnd = DateUtil.getTodayEnd();
        // 获取系统访问记录
        Long totalVisitCount = sysLogService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = sysLogService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = sysLogService.findTodayIp(dayStart, dayEnd);
        obj.put("todayIp", todayIp);
        return Result.success(obj, "获取访问量成功");
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("visitInfo")
    public Result<List<Map<String, Object>>> visitInfo() {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String, Object>> list = sysLogService.findVisitCount(dayStart, dayEnd);
        result.setResult(ConverUtil.toLowerCasePageList(list));
        return result;
    }
}