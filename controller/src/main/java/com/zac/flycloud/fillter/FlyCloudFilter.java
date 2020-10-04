package com.zac.flycloud.fillter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "FlyCloudFilter")
public class FlyCloudFilter implements FilterChain {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        log.info("ServletRequest Addr = " + servletRequest.getRemoteAddr());
    }
}
