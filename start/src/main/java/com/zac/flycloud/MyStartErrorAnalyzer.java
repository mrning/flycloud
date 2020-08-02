package com.zac.flycloud;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * 自定义自己的启动异常处理方式
 */
public class MyStartErrorAnalyzer extends AbstractFailureAnalyzer {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Throwable cause) {
        return null;
    }
}
