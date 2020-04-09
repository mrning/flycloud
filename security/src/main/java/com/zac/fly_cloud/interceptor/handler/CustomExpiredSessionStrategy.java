package com.zac.fly_cloud.interceptor.handler;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 处理用户重复登录后被踢出用户的后续操作
 */
@Component
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        sessionInformationExpiredEvent.getResponse().getWriter().write("您的账户已经在另一台机器登录，您被迫下线。");
    }
}
