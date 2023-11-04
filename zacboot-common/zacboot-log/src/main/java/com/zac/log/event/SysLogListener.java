package com.zac.log.event;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zac.base.constants.SecurityConstants;
import com.zac.log.config.LogProperties;
import com.zac.request.feign.AdminFeign;
import com.zac.request.req.admin.LogRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author lengleng 异步监听日志事件
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener implements InitializingBean {

	// new 一个 避免日志脱敏策略影响全局ObjectMapper
	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final AdminFeign adminFeign;

	private final LogProperties logProperties;

	@SneakyThrows
	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLogEventSource source = (SysLogEventSource) event.getSource();
		LogRequest sysLog = new LogRequest();
		BeanUtils.copyProperties(source, sysLog);
		adminFeign.addLog(sysLog, SecurityConstants.FROM_IN);
	}

	@Override
	public void afterPropertiesSet() {
		objectMapper.addMixIn(Object.class, PropertyFilterMixIn.class);
		String[] ignorableFieldNames = logProperties.getExcludeFields().toArray(new String[0]);

		FilterProvider filters = new SimpleFilterProvider().addFilter("filter properties by name",
				SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
		objectMapper.setFilterProvider(filters);
	}

	@JsonFilter("filter properties by name")
	class PropertyFilterMixIn {

	}

}
