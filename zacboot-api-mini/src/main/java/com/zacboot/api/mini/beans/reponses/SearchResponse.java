package com.zacboot.api.mini.beans.reponses;

import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import com.zacboot.api.mini.beans.PlatformEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@ApiModel
@Data
public class SearchResponse implements Serializable {

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品主图")
    private String goodsImageUrl;

    @ApiModelProperty(value = "最低价")
    private BigDecimal price;

    @ApiModelProperty(value = "已售卖件数")
    private String salesTip;

    @ApiModelProperty(value = "推广链接url")
    private String linkUrl;

    @ApiModelProperty(value = "小程序信息json")
    private String weAppStr;

    @ApiModelProperty(value = "平台类型")
    private String platformType;

    public static SearchResponse convertByPddSearchRes(PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem goodsItem){
        SearchResponse response = new SearchResponse();
        response.setGoodsName(goodsItem.getGoodsName());
        response.setGoodsImageUrl(goodsItem.getGoodsImageUrl());
        response.setPrice(BigDecimal.valueOf(goodsItem.getMinGroupPrice()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        response.setSalesTip(goodsItem.getSalesTip());
        response.setPlatformType(PlatformEnum.PLATFORM_PDD.getDesc());
        return response;
    }
}
