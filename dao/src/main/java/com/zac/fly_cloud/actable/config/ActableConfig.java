package com.zac.fly_cloud.actable.config;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 **/
@Configuration
@ConfigurationProperties(prefix = "cus.actable")
@MapperScan("com.zac.fly_cloud.actable.mapper")
@Data
public class ActableConfig {

    /**
     * 要扫描的model所在的pack
     */
    private String pack;

    /**
     * 默认主键
     */
    private String primarykey = "id";

    /**
     * 自动创建模式：update表示更新，create表示删除原表重新创建
     */
    private String tableAuto = "create";

}
