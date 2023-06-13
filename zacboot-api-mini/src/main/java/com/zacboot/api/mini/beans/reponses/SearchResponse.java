package com.zacboot.api.mini.beans.reponses;

import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import com.zacboot.api.mini.beans.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class SearchResponse implements Serializable {

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品主图")
    private String goodsImageUrl;

    @ApiModelProperty(value = "最低价")
    private Long price;

    @ApiModelProperty(value = "已售卖件数")
    private String salesTip;

    @ApiModelProperty(value = "推广链接url")
    private String linkUrl;

    @ApiModelProperty(value = "平台类型")
    private String platformType;

    public static SearchResponse convertByPddSearchRes(PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem goodsItem){
        SearchResponse response = new SearchResponse();
        response.setGoodsName(goodsItem.getGoodsName());
        response.setGoodsImageUrl(goodsItem.getGoodsImageUrl());
        response.setPrice(goodsItem.getMinNormalPrice());
        response.setSalesTip(goodsItem.getSalesTip());
        response.setPlatformType(Constants.PLATFORM_PDD);
        return response;
    }
}
