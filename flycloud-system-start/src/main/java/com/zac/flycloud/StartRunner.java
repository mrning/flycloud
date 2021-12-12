package com.zac.flycloud;

import com.zac.flycloud.dao.actable.service.SysMysqlCreateTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
public class StartRunner implements CommandLineRunner {

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private SysMysqlCreateTableService sysMysqlCreateTableService;

    @Override
    public void run(String... args) throws Exception {
        log.info("项目启动完成，swagger地址为：http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/swagger-ui/index.html");
        log.info("开始自动检查是否需要创建表 || 更新表");
        sysMysqlCreateTableService.createMysqlTable();
    }
}
