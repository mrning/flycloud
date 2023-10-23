package com.lqjk.third.beans;


import lombok.Data;

import java.util.Date;

/**
 * sunqipeng@2018/2/24 22:03
 **/
@Data
public class MessagePushed {
    private Long id;
    private String title;
    private String content;
    private Date readDate;
    private Date createDate;
    private boolean read;
    private String platform = "";
    private String tag;
    private int statusCode;
    private String error;
    private long messageId;
    private Long entityId;
    private int type;
    private Long ownerId;
    private boolean click = false;
    private String code;
    private boolean disable;
    private Long messageTemplateId;
    private Long updateUserId;
    private Date lastUpdate;
    //跳转类型
    private String jumpType;
    //跳转需要取的参数
    private String jumpParam;
    //大类型
    private String generalType;

    private String userNo;
    private String appChannel;
    private String thirdChannel;

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getMessageTemplateId() {
        return messageTemplateId;
    }

    public void setMessageTemplateId(Long messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpParam() {
        return jumpParam;
    }

    public void setJumpParam(String jumpParam) {
        this.jumpParam = jumpParam;
    }

    public String getGeneralType() {
        return generalType;
    }

    public void setGeneralType(String generalType) {
        this.generalType = generalType;
    }


    public Long getEntityId() {
        return entityId;
    }

    @Override
    public String toString() {
        return "MessagePushed{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", readDate=" + readDate +
                ", createDate=" + createDate +
                ", read=" + read +
                ", platform='" + platform + '\'' +
                ", tag='" + tag + '\'' +
                ", statusCode=" + statusCode +
                ", error='" + error + '\'' +
                ", messageId=" + messageId +
                ", entityId=" + entityId +
                ", type=" + type +
                ", ownerId=" + ownerId +
                ", click=" + click +
                ", code='" + code + '\'' +
                ", disable=" + disable +
                ", messageTemplateId=" + messageTemplateId +
                ", updateUserId=" + updateUserId +
                ", lastUpdate=" + lastUpdate +
                ", jumpType='" + jumpType + '\'' +
                ", jumpParam='" + jumpParam + '\'' +
                ", generalType='" + generalType + '\'' +
                '}';
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

}
