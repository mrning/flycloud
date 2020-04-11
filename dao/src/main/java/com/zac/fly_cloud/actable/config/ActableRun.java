package com.zac.fly_cloud.actable.config;

import com.zac.fly_cloud.actable.service.SysMysqlCreateTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动时自动建表
 */
@Component
public class ActableRun implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysMysqlCreateTableService sysMysqlCreateTableService;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始自动检查是否需要创建表 || 更新表");
        sysMysqlCreateTableService.createMysqlTable();
    }
}
