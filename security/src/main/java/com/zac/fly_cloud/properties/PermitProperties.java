package com.zac.fly_cloud.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置需要放开权限的url白名单
 */
@Setter
@Getter
public class PermitProperties {

    /**
     * 设置不用认证的url
     */
    private String[] httpUrls = {};

    public String[] getUrls() {

        List<String> list = new ArrayList<>();
        for (String url : httpUrls) {
            list.add(url);
        }

        list.add("/oauth/user/reg/token");
        return list.toArray(new String[list.size()]);
    }
}
