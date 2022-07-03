package com.zac.flycloud.common;

import com.alibaba.fastjson.JSON;
import com.zac.flycloud.common.basebeans.Result;
import com.zac.flycloud.common.basebeans.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BaseController {

    /**
     * 全局异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception ex){
        try {
            log.error(ex.getMessage(), ex);
            response.setHeader("content-type", "application/json;charset=UTF-8");
            // 业务异常
            if (ex instanceof BusinessException) {
                BusinessException be = (BusinessException) ex;
                response.getWriter().write(JSON.toJSONString(Result.error(be.getCode(), be.getMessage())));
                return null;
            }
            // 无权限异常
            if (ex instanceof AuthenticationException) {
                response.getWriter().write(JSON.toJSONString(Result.error("权限异常")));
                return null;
            }
            response.getWriter().write(JSON.toJSONString(Result.error(ex.getMessage() == null ? "系统繁忙" : ex.getMessage())));
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ModelMap model = new ModelMap();
            model.addAttribute("error", "未知异常");
            return new ModelAndView("error", model);
        }
    }
}
