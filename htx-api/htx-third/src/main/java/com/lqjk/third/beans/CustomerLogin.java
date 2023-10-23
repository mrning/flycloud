package com.lqjk.third.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CustomerLogin extends CustomerToken implements Serializable {
    private Long id;
    private String code;
    private boolean disable;
    private String email;
    private boolean lock;
    private String name;
    private String password;
    private boolean passwordLogin;
    private boolean phoneLogin;
    private String phoneNumber;
    private String username;
    private Long ownerId;
    private Long userId;
    private String qrMetroUserCode;
    private String nfcMetroUserCode;
    private String newNfcMetroUserCode;
    private String currentMode;
    private String agreementCode;
    private String failedLogin;
    private String hardWareCode;
    private String loginToken;
    private String qqId;
    private Date createDate;
    private Date lastUpdate;
    private boolean qqLogin;
    private String randomSeq;
    private String securityAnswer;
    private String securityQuestion;
    private Long successLogin;
    private String sinaId;
    private String tokenValidDate;
    private String tokenDate;
    private String token;
    private String tokenChannel;
    private boolean weiboLogin;
    private boolean wechatLogin;
    private String weiboId;
    private String wechatId;
    private Long updateUserId;
    private String appChannel;
    private String userNo;
    /**
     * 在缓存中的用户信息可能会存在此字段，不加上会出现解析异常
     */
    private String nickName;
    private String image;
    private String walletAdd;

    private String thirdChannel;

    public String getImage() {
        return image;
    }

    public String getWalletAdd() {
        return walletAdd;
    }

    public void setWalletAdd(String walletAdd) {
        this.walletAdd = walletAdd;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 是否完成新手任务
     */
    private boolean missionAccomplished;
    /**
     * 完成时间
     */
    private Date missionTime;

    private List<String> cardNoList;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(boolean passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public boolean isPhoneLogin() {
        return phoneLogin;
    }

    public void setPhoneLogin(boolean phoneLogin) {
        this.phoneLogin = phoneLogin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQrMetroUserCode() {
        return qrMetroUserCode;
    }

    public void setQrMetroUserCode(String qrMetroUserCode) {
        this.qrMetroUserCode = qrMetroUserCode;
    }

    public String getNfcMetroUserCode() {
        return nfcMetroUserCode;
    }

    public void setNfcMetroUserCode(String nfcMetroUserCode) {
        this.nfcMetroUserCode = nfcMetroUserCode;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public String getAgreementCode() {
        return agreementCode;
    }

    public void setAgreementCode(String agreementCode) {
        this.agreementCode = agreementCode;
    }

    @Override
    public String toString() {
        return "CustomerLogin{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", disable=" + disable +
                ", email='" + email + '\'' +
                ", lock=" + lock +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", passwordLogin=" + passwordLogin +
                ", phoneLogin=" + phoneLogin +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", ownerId=" + ownerId +
                ", userId=" + userId +
                ", qrMetroUserCode='" + qrMetroUserCode + '\'' +
                ", nfcMetroUserCode='" + nfcMetroUserCode + '\'' +
                ", newNfcMetroUserCode='" + newNfcMetroUserCode + '\'' +
                ", currentMode='" + currentMode + '\'' +
                ", agreementCode='" + agreementCode + '\'' +
                '}';
    }

    public String getFailedLogin() {
        return failedLogin;
    }

    public void setFailedLogin(String failedLogin) {
        this.failedLogin = failedLogin;
    }

    public String getHardWareCode() {
        return hardWareCode;
    }

    public void setHardWareCode(String hardWareCode) {
        this.hardWareCode = hardWareCode;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isQqLogin() {
        return qqLogin;
    }

    public void setQqLogin(boolean qqLogin) {
        this.qqLogin = qqLogin;
    }

    public String getRandomSeq() {
        return randomSeq;
    }

    public void setRandomSeq(String randomSeq) {
        this.randomSeq = randomSeq;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public Long getSuccessLogin() {
        return successLogin;
    }

    public void setSuccessLogin(Long successLogin) {
        this.successLogin = successLogin;
    }

    public String getSinaId() {
        return sinaId;
    }

    public void setSinaId(String sinaId) {
        this.sinaId = sinaId;
    }

    public String getTokenValidDate() {
        return tokenValidDate;
    }

    public void setTokenValidDate(String tokenValidDate) {
        this.tokenValidDate = tokenValidDate;
    }

    public String getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(String tokenDate) {
        this.tokenDate = tokenDate;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    public boolean isWeiboLogin() {
        return weiboLogin;
    }

    public void setWeiboLogin(boolean weiboLogin) {
        this.weiboLogin = weiboLogin;
    }

    public boolean isWechatLogin() {
        return wechatLogin;
    }

    public void setWechatLogin(boolean wechatLogin) {
        this.wechatLogin = wechatLogin;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }


    public String getNewNfcMetroUserCode() {
        return newNfcMetroUserCode;
    }

    public void setNewNfcMetroUserCode(String newNfcMetroUserCode) {
        this.newNfcMetroUserCode = newNfcMetroUserCode;
    }

    public boolean getMissionAccomplished() {
        return missionAccomplished;
    }

    public void setMissionAccomplished(boolean missionAccomplished) {
        this.missionAccomplished = missionAccomplished;
    }

    public Date getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Date missionTime) {
        this.missionTime = missionTime;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getTokenChannel() {
        return tokenChannel;
    }

    public void setTokenChannel(String tokenChannel) {
        this.tokenChannel = tokenChannel;
    }
}
