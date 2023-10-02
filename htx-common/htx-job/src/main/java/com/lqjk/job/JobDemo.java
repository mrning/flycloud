package com.lqjk.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * xxl-job 定时任务
 */
@Slf4j
@Component
public class JobDemo {

    /**
     * 注解中的值对应 xxl-job-admin 添加任务表单中额  JobHandler 字段
     */
    @XxlJob("demotask")
    public void demotask() {
        log.info(">>>>>>>>>>> xxl-job demotask start");
        XxlJobHelper.log("TASK[demotask] start.");
        // 处理执行结果，可以在任务管理页面 [调度日志] 菜单中的 [执行备注] 字段查看
        XxlJobHelper.handleSuccess("执行成功");
    }
}
