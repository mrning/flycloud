package com.lqjk.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置信息
 */
@Configuration
@RefreshScope
public class GatewayRoutersConfiguration {
    public String serverAddr;
    public String namespace;
    public String dataId;
    public String routeGroup;

    @Value("${htx.route.config.data-id:#{null}}")
    public void setRouteDataId(String dataId) {
        this.dataId = dataId + ".json";
    }

    @Value("${spring.cloud.nacos.config.group:DEFAULT_GROUP:#{null}}")
    public void setRouteGroup(String routeGroup) {
        this.routeGroup = routeGroup;
    }

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    @Value("${spring.cloud.nacos.config.namespace:#{null}}")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getDataId() {
        return dataId;
    }

    public String getRouteGroup() {
        return routeGroup;
    }

}
