package com.zac.security.component;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zac.base.basebeans.exceptions.BusinessException;
import com.zac.base.constants.RedisKey;
import com.zac.base.constants.SecurityConstants;
import com.zac.base.enums.UserClientEnum;
import com.zac.base.utils.RedisUtil;
import com.zac.security.config.HtxSecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class HtxAuthenticationFilter extends OncePerRequestFilter {

    private final RedisUtil redisUtil;
    private final PermitAllUrlProperties allUrlProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取认证信息
        final String headerClient = request.getHeader(SecurityConstants.CLIENT);
        if (StringUtils.isNotBlank(headerClient) && null == UserClientEnum.getByValue(headerClient)) {
            log.warn("请求头中的client值当前不支持: {}", headerClient);
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeaderToken = request.getHeader("token");
        if (authHeaderToken == null || allUrlProperties.getUrls().contains(request.getRequestURI())) {
            log.warn("请求头中没有token，或当前请求url不需要认证: {}", request.getRequestURL());
            filterChain.doFilter(request, response);
            return;
        }
        // 从token中解析出user uuid
        String userUuid = String.valueOf(StpUtil.getLoginIdByToken(authHeaderToken));
        if (userUuid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = getUserDetails(authHeaderToken, headerClient);

            // 如果令牌有效，封装一个UsernamePasswordAuthenticationToken对象
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    // 用户凭证
                    null,
                    userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 更新安全上下文的持有用户
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

    @NotNull
    private UserDetails getUserDetails(String authHeaderToken, String headerClient) {
        // 登录后将用户信息缓存
        Object o = redisUtil.get(UserClientEnum.ADMIN.getValue() + ":" + RedisKey.LOGIN_SYSTEM_USERINFO + authHeaderToken);
        if (null == o) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), authHeaderToken + ", token已过期");
        }

        JSONObject resObj = JSONUtil.parseObj(Objects.requireNonNull(o));
        log.info("请求token: " + authHeaderToken + ", 该token对应的缓存用户信息: " + resObj);

        JSONArray roleArray = resObj.getJSONArray("roles");
        List<String> roles = roleArray.stream().map(o1 -> SecurityConstants.ROLE + ((JSONObject) o1).getStr("roleCode")).collect(Collectors.toList());

        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

        JSONObject userInfo = resObj.getJSONObject("userInfo");
        return new HtxSecurityUser(userInfo.getLong("id"), userInfo.getStr("username"),
                userInfo.getStr("password"), userInfo.getStr("phone"),
                true, true, true, true, authorities);
    }
}
