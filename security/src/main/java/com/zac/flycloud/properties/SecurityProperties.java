package com.zac.flycloud.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "flycloud.security")
public class SecurityProperties {
    private PermitProperties ignore = new PermitProperties();
}

