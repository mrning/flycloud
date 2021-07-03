package com.zac.flycloud;

import com.zac.flycloud.basebean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.StringJoiner;

/**
 * 异常处理器
 * 
 * @Author scott
 * @Date 2019
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error(404, "路径不存在，请检查路径是否正确");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DuplicateKeyException.class)
	public Result<?> handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({AuthenticationException.class})
	public Result<?> handleAuthorizationException(AuthenticationException e){
		log.error(e.getMessage(), e);
		return Result.noauth("认证失败，请联系管理员授权");
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，"+e.getMessage());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
	public Result<?> handleException(HttpMessageNotReadableException e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，参数格式异常无法转换"+e.getMessage());
	}
	
	/**
	 * @Author zac
	 * @param
	 * @return
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		StringBuffer sb = new StringBuffer();
		sb.append("不支持");
		sb.append(e.getMethod());
		sb.append("请求方法，");
		String [] methods = e.getSupportedMethods();
		if(methods!=null){
			sb.append("支持以下");
			StringJoiner joiner = new StringJoiner("、");
			for(String str:methods){
				joiner.add(str);
			}
			sb.append(joiner);
		}
		log.error(sb.toString(), e);
		return Result.error(405,sb.toString());
	}
	
	 /** 
	  * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException 
	  */
    @org.springframework.web.bind.annotation.ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	log.error(e.getMessage(), e);
        return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    	log.error(e.getMessage(), e);
        return Result.error("字段太长,超出数据库字段的长度");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PoolException.class)
    public Result<?> handlePoolException(PoolException e) {
    	log.error(e.getMessage(), e);
        return Result.error("Redis 连接异常!");
    }

}
