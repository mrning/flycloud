package com.zac.fly_cloud.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * 自定义资源权限过滤器，实现动态权限验证
 */
@Component
public class SecurityFilterInvocatioin implements FilterInvocationSecurityMetadataSource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 访问一个url时，返回这个url需要的访问权限
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //去数据库查询菜单资源
//        List<Menu> allMenu = menuService.getAllMenu();
//        for (Menu menu : allMenu) {
//            if (antPathMatcher.match(menu.getUrl(), requestUrl)
//                    && menu.getRoles().size() > 0) {
//                List<Role> roles = menu.getRoles();
//                int size = roles.size();
//                String[] values = new String[size];
//                for (int i = 0; i < size; i++) {
//                    values[i] = roles.get(i).getName();
//                }
//                log.info("当前访问路径是{},这个url所需要的访问权限是{}", requestUrl, values);
//                return SecurityConfig.createList(values);
//            }
//        }
        /**
         * @Author: Galen
         * @Description: 如果本方法返回null的话，意味着当前这个请求不需要任何角色就能访问
         * 此处做逻辑控制，如果没有匹配上的，返回一个默认具体权限，防止漏缺资源配置
         **/
        log.info("当前访问路径是{},这个url所需要的访问权限是{}", requestUrl, "ROLE_LOGIN");
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 返回类对象是否支持校验
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

}
