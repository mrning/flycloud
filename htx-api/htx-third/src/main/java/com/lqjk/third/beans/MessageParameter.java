package com.lqjk.third.beans;

import com.alibaba.nacos.shaded.com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class MessageParameter {

    public static int REGISTER = 1;
    public static int EXCHANGE = 2;
    public static int STEP = 3;
    public static int STEP_LUCKY = 4;

    public static int COMPANY_INVITE = 6;
    public static int GUTSCHEIN = 7;

    // 实践证书
    public static int CERTIFICATE = 8;

    // 重新提交证书申请
    public static int REPEAT_CER = 9;

    //头条
    public static int HEADLINE = 7;

    private String phone;
    private String email;
    private String wechatAuthId;
    private String title;
    private String content;
    private Map<String, Object> parameter = Maps.newHashMap();
    private Long ownerId;
    private long entityId;
    private String code;
    private int type;
    private boolean success;
    private String response;
    private String flag;
    private String msgGroup;
    //跳转类型
    private String jumpType;
    //跳转参数
    private String jumpParam;
    //大分类
    private String generalType;

    private String userNo;
    private String appChannel;
    private String thirdChannel;

    public MessageParameter() {
    }

    public MessageParameter(String title, String content, Long ownerId, int type, String jumpType, String jumpParam, String generalType) {
        this.title = title;
        this.content = content;
        this.ownerId = ownerId;
        this.type = type;
        this.jumpType = jumpType;
        this.jumpParam = jumpParam;
        this.generalType = generalType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String title;

        private String content;

        private Long ownerId;

        private int type;

        //跳转类型
        private String jumpType;
        //跳转参数
        private String jumpParam;
        //大分类
        private String generalType;


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setJumpType(String jumpType) {
            this.jumpType = jumpType;
            return this;
        }

        public Builder setJumpParam(String jumpParam) {
            this.jumpParam = jumpParam;
            return this;
        }

        public Builder setGeneralType(String generalType) {
            this.generalType = generalType;
            return this;
        }

        public MessageParameter build() {
            return new MessageParameter(title, content, ownerId, type, jumpType, jumpParam, generalType);
        }
    }

}