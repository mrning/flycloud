package com.zac.flycloud.basebean;

import com.zac.flycloud.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 * @author zac
 * @date  2019年1月19日
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class DataResponseResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	@ApiModelProperty(value = "成功标志")
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	@ApiModelProperty(value = "返回处理消息")
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	@ApiModelProperty(value = "返回代码")
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	@ApiModelProperty(value = "返回数据对象")
	private T result;

	/**
	 * 时间戳
	 */
	@ApiModelProperty(value = "时间戳")
	private long timestamp = System.currentTimeMillis()/1000;

	public DataResponseResult() {
		
	}
	
	
	public static DataResponseResult<Object> success() {
		DataResponseResult<Object> r = new DataResponseResult<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult("success");
		r.setMessage("成功");
		return r;
	}
	
	public static DataResponseResult<Object> success(String msg) {
		DataResponseResult<Object> r = new DataResponseResult<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult("success");
		r.setMessage(msg);
		return r;
	}

	public static<T> DataResponseResult<T> success(T data) {
		DataResponseResult<T> r = new DataResponseResult<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		r.setMessage("成功");
		return r;
	}

	public static<T> DataResponseResult<T> success(T data, String message) {
		DataResponseResult<T> r = new DataResponseResult<T>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		r.setMessage(message);
		return r;
	}

	
	public static DataResponseResult<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}
	
	public static DataResponseResult<Object> error(int code, String msg) {
		DataResponseResult<Object> r = new DataResponseResult<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public DataResponseResult<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}

	public DataResponseResult<T> error404(String message) {
		this.message = message;
		this.code = CommonConstant.SC_RESOURCE_NOTFOUND_ERROR_404;
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static DataResponseResult<Object> noauth(String msg) {
		return error(CommonConstant.SC_JEECG_NO_AUTHZ, msg);
	}
}