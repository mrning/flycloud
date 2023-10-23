package com.lqjk.third.beans;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class CancelAuthVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * app渠道:qdmetro-青岛地铁
     */
    @NotBlank(message = "app渠道不能为空")
    private String channel;

    /**
     * 授权类型：01-地铁；02-公交
     */
    private String authType;

    /**
     * 授权id
     */
    @NotBlank(message = "授权id不能为空")
    private String authToken;
}
