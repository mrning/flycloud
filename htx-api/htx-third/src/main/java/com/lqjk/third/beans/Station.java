package com.lqjk.third.beans;



import com.lqjk.base.basebeans.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class Station extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;
    private Long id;

    private boolean hotStation;
    /**
     * 车站名称
     */
    private String name;
    //    坐标
    private BigDecimal xLocation;
    //    坐标
    private BigDecimal yLocation;
    //    是否换乘站
    private boolean exchangeStation;
    private String svgXLocation;
    private String textLocation;
    private String svgYLocation;
    private String svgTXLocation;
    private String svgTYLocation;
    //属于哪个线，多个情况需要额外考虑
    private String lineName;
    private String pinyin;
    private String pinyinShort;
    private String stationImage;
    private String svgLocation;
    //是否是始发站
    private boolean originStop;
    private String xls;
    private String yls;
    private String itpCode;
    private String itpNameCn;
    private String itpNameEn;

    public String getXls() {
        if (xLocation == null)
            return "";
        return xLocation.toPlainString();
    }

    public void setXls(String xls) {
        this.xls = xls;
    }

    public String getYls() {
        if (yLocation == null)
            return "";
        return yLocation.toPlainString();
    }

    public void setYls(String yls) {
        this.yls = yls;
    }
}
