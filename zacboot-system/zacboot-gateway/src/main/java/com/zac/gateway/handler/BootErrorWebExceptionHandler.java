package com.zac.gateway.handler;

import com.zac.base.basebeans.Result;
import cn.dev33.satoken.exception.NotLoginException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 处理Gateway异常捕获后统一返回值
 */
@Configuration
public class BootErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        //1.获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //2.response是否结束  用于多个异常处理时候
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        //2.设置响应头类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //3.设置响应状态吗
        if (ex instanceof NotLoginException) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        } else {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //4.设置响应内容
        return response.writeWith(Mono.fromSupplier(() -> {
            try {
                //设置响应到response的数据
                DataBufferFactory bufferFactory = response.bufferFactory();
                ObjectMapper objectMapper = new ObjectMapper();
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(Result.error(Objects.requireNonNull(response.getStatusCode()).value(),ex.getMessage())));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }));
    }
}
