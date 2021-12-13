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

    @Override
    public void run(String... args) throws Exception {
        log.info("项目启动完成，swagger地址为：http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/swagger-ui/index.html");
    }
}
