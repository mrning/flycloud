package com.zacboot.admin.property.dto.request;

import com.zacboot.system.core.entity.administration.AppProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AppPropertyPageRequest {

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
    private Date buyDate;

    /**
     * 领用日期
     */
    private Date useDate;

    /**
     * 分类编码
     */
    private String typeCode;


    /**
     * 使用部门名称
     */
    private String useDeptName;


    /**
     * 使用人姓名
     */
    private String ownerName;

    /**
     * 存放位置
     */
    private String location;

    private Long page;

    private Long limit;


    public AppProperty converToDo(){
        AppProperty appProperty = new AppProperty();
        BeanUtils.copyProperties(this,appProperty);
        return appProperty;
    }
}
