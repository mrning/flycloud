package com.zac.flycloud.api.admin;

import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.BaseController;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysLogDTO;
import com.zac.flycloud.service.SysLogService;
import com.zac.flycloud.utils.ConverUtil;
import com.zac.flycloud.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * AutoCreateFile 日志相关 
 * @date 2021年4月24日星期六
 * @author zac
 */
@Api(tags = "日志相关")
@RestController
@RequestMapping("/api/admin/sysLog")
@Slf4j
public class AdminSysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * AutoCreateFile add
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/add")
    public DataResponseResult<Integer> add(@RequestBody SysLogDTO sysLogDTO) {
        return DataResponseResult.success(sysLogService.add(sysLogDTO));
    }

    /**
     * AutoCreateFile del
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/del")
    public DataResponseResult<Integer> del(@RequestBody SysLogDTO sysLogDTO) {
        return DataResponseResult.success(sysLogService.del(sysLogDTO));
    }

    /**
     * AutoCreateFile update
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/update")
    public DataResponseResult<Integer> update(@RequestBody SysLogDTO sysLogDTO) {
        return DataResponseResult.success(sysLogService.update(sysLogDTO));
    }

    /**
     * AutoCreateFile queryPage
     * @date 2021年4月24日星期六
     * @author zac
     */
    @PostMapping("/queryPage")
    public DataResponseResult<PageResult<SysLogDTO>> queryPage(@RequestBody SysLogDTO sysLogDTO) {
        return DataResponseResult.success(sysLogService.queryPage(sysLogDTO));
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @ApiOperation("获取全部/当天访问量/ip数")
    @GetMapping("loginfo")
    public DataResponseResult<Object> loginfo() {
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
        return DataResponseResult.success(obj, "获取访问量成功");
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("visitInfo")
    @ApiOperation("获取最近一周访问数量/ip数量")
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
        List<Map<String, Object>> list = sysLogService.findVisitCount(dayStart, dayEnd);
        result.setResult(ConverUtil.toLowerCasePageList(list));
        return result;
    }
}