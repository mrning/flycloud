package com.zacboot.api.mini.beans.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 搜索请求
 */
@Data
public class SearchRequest {

    @ApiModelProperty("商品信息")
    private String goodsInfo;

    private Integer page = 0;
    private Integer pageSize = 10;

}
