package com.lqjk.third.beans;


import com.lqjk.base.annotation.AutoColumn;
import com.lqjk.base.basebeans.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLogin extends BaseEntity {
    //主键id
    private Long    id;
    //验证码
    private String  code;
    //创建时间
    private Date    createDate;
    //是否注销
    private boolean disable;
    //邮箱
    private String  email;
    //登录失败次数
    private Long    failedLogin;
    //
    private String  hardWareCode;
    //是否锁定
    private boolean lock;
    // 旧版本登录token
    private String  loginToken;
    //最后修改时间
    private Date    lastUpdate;
    //顾客姓名
    private String  name;
    //登录密码（初始密码统一888888）
    private String  password;
    //是否密码登录
    private boolean passwordLogin;
    //是否为短信验证码登录
    private boolean phoneLogin;
    //用户注册手机号
    private String  phoneNumber;
    //QQ登录协议ID
    private String  qqId;
    //是否为QQ登录
    private boolean qqLogin;
    //用户随机因子
    private String  randomSeq;
    //找回账号时的安全问题
    private String  securityAnswer;
    //找回账号时的安全问题答案
    private String  securityQuestion;
    //登陆成功次数
    private Long    successLogin;
    //新浪微博登录的授权ID（老版本字段，新版本暂不使用）
    private String  sinaId;
    //老版本字段，新版本暂不使用
    private String  tokenValidDate;
    //老版本字段，新版本暂不使用
    private String  tokenDate;
    //老版本字段，新版本暂不使用
    private String  token;
    //用户名
    private String  username;
    //是否微博登录（老版本字段，新版本暂不使用）
    private boolean weiboLogin;
    //是否微信登录（老版本字段，新版本暂不使用）
    private boolean wechatLogin;
    //微博ID（老版本字段，新版本暂不使用）
    private String  weiboId;
    //微信ID（老版本字段，新版本暂不使用）
    private String  wechatId;
    //用户ID（所有业务使用此ID）
    private Long    ownerId;
    //谁更新了这个用户信息
    private Long    updateUserId;
    //APP生成的用户ID（app自用，userId对内，ownerId对外）
    private Long    userId;
    //二维码逻辑卡号
    private String  qrMetroUserCode;
    //nfc逻辑卡号
    private String  nfcMetroUserCode;
    private String  newNfcMetroUserCode;
    //用户当前过闸方式 02是二维码 03 是NFC
    private String  currentMode;
    //用户当前默认支付渠道的签约流水号
    private String  agreementCode;
    //城市
    private String  city;
    //用户权限表对应的ID
    @AutoColumn(isIgnore = true)
    private com.lqjk.base.bizentity.SysUser SysUser;
    //余额
    private String avlBal;
    //累计金额
    private Integer totalAmt;

    private String loginCity;

    private String openWallet;
    private String appChannel;
}
