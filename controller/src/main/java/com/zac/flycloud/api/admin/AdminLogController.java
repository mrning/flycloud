package com.zac.flycloud.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.log.SysLogService;
import com.zac.flycloud.utils.ConverUtil;
import com.zac.flycloud.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(tags = "日志相关")
@Slf4j
@RestController
@RequestMapping("/api/admin/log")
public class AdminLogController {

    @Autowired
    private SysLogService logService;

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
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
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
        List<Map<String, Object>> list = logService.findVisitCount(dayStart, dayEnd);
        result.setResult(ConverUtil.toLowerCasePageList(list));
        return result;
    }


}


