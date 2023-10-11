package com.lqjk.security.component;

import com.lqjk.base.utils.UrlIPUtil;
import com.lqjk.security.annotation.Inner;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author lengleng
 * @date 2022-06-04
 *
 * 服务间接口不鉴权处理逻辑
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class HtxSecurityInnerAspect implements Ordered {

	private final HttpServletRequest request;

	@SneakyThrows
	@Before("@within(inner) || @annotation(inner)")
	public void around(JoinPoint point, Inner inner) {
		// 实际注入的inner实体由表达式后一个注解决定，即是方法上的@Inner注解实体，若方法上无@Inner注解，则获取类上的
		if (inner == null) {
			Class<?> clazz = point.getTarget().getClass();
			inner = AnnotationUtils.findAnnotation(clazz, Inner.class);
		}
		log.warn("来源ip {} ,访问无需鉴权接口 {}", UrlIPUtil.getIpAddr(request), point.getSignature().getName());
		// 这里做接口级别的权限拦截处理
//		String header = request.getHeader(SecurityConstants.FROM);
//        if (inner != null && inner.value() && !StrUtil.equals(SecurityConstants.FROM_IN, header)) {
//			log.warn("来源ip {} ,访问接口 {} 没有权限", UrlIPUtil.getIpAddr(request), point.getSignature().getName());
//			throw new AccessDeniedException("Access is denied");
//		}
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}

}
