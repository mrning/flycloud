package com.lqjk.third.beans;


import com.lqjk.base.basebeans.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInformation extends BaseEntity {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1546092943803945577L;
	//QQ
    private String qq;
    //微信
    private String wechat;
    //邮箱
    private String email;
    //手机号
    private String mobilePhone;
    //地址
    private String address;
    //
    private String postCode;
    //
    private String idCode;
    //头像
    private String image;
    //
    private boolean acceptPush;
    //
    private boolean rangeAccept;
    //
    private String acceptBegin;
    //
    private String acceptEnd;
    //昵称
    private String nickName;
    //
    private BigDecimal creditSum;
    //
    private String gender;
    //
    private Date dob;
    //
    private String policy;
    //
    private String company;
    //
    private String nation;
    //
    private String educationBackground;
    //
    private String position;
    //
    private String workExp;
    //
    private String advantage;

    private Long ownerIdEx;

    /**
     * 短信标识
     */
    private Boolean smsFlag;

    private String userNo;

    private String appChannel;

    private Long sysUserLoginId;

}
