package com.zac.fly_cloud;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Data
@ToString
public class DataResponse<T> {
    private String message = "成功";
    private Object data;
    private String code;

    public DataResponse(Object data, String message, String code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public static <T> DataResponse success(Object data, String message) {
        return new DataResponse(data, message, ResponseConstants.SUCCESS_CODE);
    }

    public static <T> DataResponse fail(String message, String code) {
        return new DataResponse("fail", message, StringUtils.isNotBlank(code) ? code : ResponseConstants.FAIL_CODE);
    }

    public static <T> DataResponse fail(String message) {
        return fail(message,null);
    }

    public static <T> DataResponse fail(Object data, String message) {
        return new DataResponse(data, message, ResponseConstants.FAIL_CODE);
    }


}
