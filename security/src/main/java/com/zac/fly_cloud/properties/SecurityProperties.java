package com.zac.fly_cloud.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "cus.security")
public class SecurityProperties {
    private PermitProperties ignore = new PermitProperties();
}

