package com.lqjk.third.beans;

import java.util.Date;

public class TeamCompanyUser {
    private Long serialId;

    private Long comId;

    private Long ownerId;

    private Date joinTime;

    private Date leaveTime;

    private Integer status;

    private String joinCode;

    private Date inviteTime;

    private String inviteUser;

    private Date inviteExpiredTime;

    private Integer joinUploadStatus;

    private Integer leaveUploadStatus;

    private Integer readFlag;

    public Long getSerialId() {
        return serialId;
    }

    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode == null ? null : joinCode.trim();
    }

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser == null ? null : inviteUser.trim();
    }

    public Date getInviteExpiredTime() {
        return inviteExpiredTime;
    }

    public void setInviteExpiredTime(Date inviteExpiredTime) {
        this.inviteExpiredTime = inviteExpiredTime;
    }

    public Integer getJoinUploadStatus() {
        return joinUploadStatus;
    }

    public void setJoinUploadStatus(Integer joinUploadStatus) {
        this.joinUploadStatus = joinUploadStatus;
    }

    public Integer getLeaveUploadStatus() {
        return leaveUploadStatus;
    }

    public void setLeaveUploadStatus(Integer leaveUploadStatus) {
        this.leaveUploadStatus = leaveUploadStatus;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }
}