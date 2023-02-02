package com.zacboot.api.sp.controller;

import com.zacboot.api.sp.beans.reponses.SearchResponse;
import com.zacboot.api.sp.beans.requests.SearchRequest;
import com.zacboot.api.sp.service.BaseApiService;
import com.zacboot.common.base.basebeans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @Autowired
    private BaseApiService baseApiService;

    /**
     * 商品搜索
     * @param request
     * @return
     */
    @PostMapping("/search")
    public Result<SearchResponse> search(@RequestBody SearchRequest request) throws Exception {
        return Result.success(baseApiService.searchGoods(request));
    }
}
