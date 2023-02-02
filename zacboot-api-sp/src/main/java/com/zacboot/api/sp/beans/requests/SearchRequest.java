package com.zacboot.api.sp.beans.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 搜索请求
 */
@Data
public class SearchRequest {

    @ApiModelProperty("商品名称")
    private String goodsName;

}
