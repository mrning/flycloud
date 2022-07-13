package com.zacboot.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.zacboot.admin.beans.entity.SysLog;
import com.zacboot.admin.beans.vos.request.SysLogRequest;
import com.zacboot.admin.service.SysLogService;
import com.zacboot.common.base.utils.ConverUtil;
import com.zacboot.common.base.utils.DateUtils;
import com.zacboot.common.base.basebeans.PageResult;
import com.zacboot.common.base.basebeans.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * AutoCreateFile 日志相关 
 * @date 2021年4月24日星期六
 * @author zac
 */
@Api(tags = "日志相关")
@RestController
@RequestMapping("/admin/sysLog")
@Slf4j
public class AdminSysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * AutoCreateFile add
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysLog sysLog) {
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
    @ApiOperation("获取全部/当天访问量/ip数")
    @GetMapping("loginfo")
    public Result<Object> loginfo() {
        JSONObject obj = new JSONObject();
        // 获取一天的开始和结束时间
        Date dayStart = DateUtils.getTodayStart();
        Date dayEnd = DateUtils.getTodayEnd();
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
    @ApiOperation("获取最近一周访问数量/ip数量")
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