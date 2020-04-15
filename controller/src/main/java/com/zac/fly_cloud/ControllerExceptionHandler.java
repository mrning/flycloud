package com.zac.fly_cloud;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 将controller中的异常统一处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DataResponse handleException(Exception e) {

        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            return DataResponse.fail(String.valueOf(pw), e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return DataResponse.fail(ex.getMessage());
        }
    }
}
