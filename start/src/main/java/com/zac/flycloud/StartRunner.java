package com.zac.flycloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
public class StartRunner implements ApplicationRunner {

    @Value("${server.port}")
    private Integer port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
      log.info("项目启动完成，swagger地址为：http://"+ InetAddress.getLocalHost().getHostAddress()+":"+port+"/swagger-ui/index.html");
    }
}
