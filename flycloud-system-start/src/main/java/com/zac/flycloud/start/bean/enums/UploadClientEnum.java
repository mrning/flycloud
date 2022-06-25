package com.zac.flycloud.start.bean.enums;

/**
 * 上传客户端类型
 */
public enum UploadClientEnum {
    OSS_ALI_CLIENT("阿里oss对象存储"),
    OSS_TENCENT_CLIENT("腾讯oss对象存储");

    private final String desc;

    UploadClientEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
