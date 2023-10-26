package com.lqjk.request;

import com.lqjk.base.constants.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 * @author zac
 * @date  2019年1月19日
 */
@Data
public class FeignResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	private T result;

	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis()/1000;

	private enum ResEnum {
		SUCCESS("SUCCESS","成功"),
		FAIL("FAIL","失败"),
		UNAUTHORIZED("401", "暂未登录或token已经过期"),
		FORBIDDEN("403", "没有相关权限");

		private final String code;
		private final String message;

		ResEnum(String code, String message) {
			this.code = code;
			this.message = message;
		}
	}

	public static FeignResult<String> success() {
		FeignResult<String> r = new FeignResult<>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(ResEnum.SUCCESS.message);
		r.setResult(ResEnum.SUCCESS.message);
		return r;
	}
	
	public static FeignResult<String> success(String data) {
		FeignResult<String> r = new FeignResult<>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		r.setMessage(ResEnum.SUCCESS.message);
		return r;
	}

	public static<T> FeignResult<T> success(T data) {
		FeignResult<T> r = new FeignResult<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		r.setMessage(ResEnum.SUCCESS.message);
		return r;
	}

	public static<T> FeignResult<T> success(T data, String message) {
		FeignResult<T> r = new FeignResult<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		r.setMessage(message);
		return r;
	}

	public static FeignResult<String> success(ResEnum resEnum) {
		return success(resEnum.code,resEnum.message);
	}

	
	public static FeignResult<Object> error() {
		return error(ResEnum.FAIL.message);
	}
	public static FeignResult<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}

	public static FeignResult<Object> error(int code, String msg) {
		FeignResult<Object> r = new FeignResult<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}
	public static<T> FeignResult<T> error(int code, String msg, T data) {
		FeignResult<T> r = new FeignResult<T>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		r.setResult(data);
		return r;
	}

	public static<T> FeignResult<T> error(String msg, T data) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500,msg,data);
	}

	public FeignResult<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}

	public FeignResult<T> error404(String message) {
		this.message = message;
		this.code = CommonConstant.SC_RESOURCE_NOTFOUND_ERROR_404;
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static FeignResult<Object> noauth(String msg) {
		return error(CommonConstant.SC_NO_AUTH, msg);
	}

	/**
	 * 未登录返回结果
	 */
	public static <T> FeignResult<T> unauthorized(T data) {
		return error(CommonConstant.UNAUTHORIZED, ResEnum.UNAUTHORIZED.message, data);
	}
}