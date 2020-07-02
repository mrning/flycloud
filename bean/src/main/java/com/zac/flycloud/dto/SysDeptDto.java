package com.zac.flycloud.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SysDeptDto {

    /**ID*/
    private String id;
    /**部门树icon*/
    private String icon;
    /**父机构ID*/
    private String parentId;
    /**是否叶子节点*/
    private boolean leaf;
    /**机构/部门名称*/
    private String departName;
    /**描述*/
    private String description;
    /**手机号*/
    private String mobile;
    /**传真*/
    private String fax;
    /**地址*/
    private String address;
    /**备注*/
    private String memo;
    /**状态（1启用，0不启用）*/
    private String status;
    /**删除状态（0，正常，1已删除）*/
    private String delFlag;
    /**创建人*/
    private String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**更新人*/
    private String updateBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
