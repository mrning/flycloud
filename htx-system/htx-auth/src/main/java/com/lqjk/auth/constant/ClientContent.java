package com.lqjk.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户端类型
 */
@Getter
@AllArgsConstructor
public enum ClientContent {

    /**
     * 管理后台
     */
    CLIENT_ADMIN("admin","AdminService"),
    CLIENT_APP("app","AppService");

    private final String clientId;
    private final String clientService;

}
