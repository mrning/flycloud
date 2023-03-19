package com.zacboot.api.mini.controller;

import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;
import com.zacboot.api.mini.service.GoodsService;
import com.zacboot.common.base.basebeans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品搜索
     * @param request
     * @return
     */
    @PostMapping("/search")
    public Result<List<SearchResponse>> search(@RequestBody SearchRequest request) throws Exception {
        return Result.success(goodsService.searchGoods(request));
    }
}
