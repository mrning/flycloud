package com.zac.flycloud.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "flycloud.security")
public class SecurityProperties {
    private PermitProperties ignore = new PermitProperties();
}

