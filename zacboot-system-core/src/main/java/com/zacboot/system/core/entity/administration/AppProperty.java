package com.zacboot.system.core.entity.administration;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zacboot.system.core.entity.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("app_property")
public class AppProperty extends BaseEntity {

    /**
     * 资产编号
     */
    private String propertyNo;

    /**
     * 资产名称
     */
    private String propertyName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 已领用数量
     */
    private Integer useTotalCount;

    /**
     * 剩余库存数量
     */
    private Integer remainCount;

    /**
     * 规格型号
     */
    private String specModel;

    /**
     * 采购日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime buyDate;

    /**
     * 领用日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime useDate;

    /**
     * 分类编码
     */
    private String typeCode;

    /**
     * 使用部门id
     */
    private String useDeptUuid;

    /**
     * 使用部门名称
     */
    private String useDeptName;

    /**
     * 使用人id
     */
    private String ownerUuid;

    /**
     * 使用人姓名
     */
    private String ownerName;

    /**
     * 存放位置
     */
    private String location;

    /**
     * 备注
     */
    private String remark;
}
