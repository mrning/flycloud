package com.zacboot.api.mini.service.impl;

import com.zacboot.api.mini.beans.Constants;
import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;
import com.zacboot.api.mini.factory.ApiServiceFactory;
import com.zacboot.api.mini.service.BaseApiService;
import com.zacboot.api.mini.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private ApiServiceFactory apiServiceFactory;

    @Override
    public List<SearchResponse> searchGoods(SearchRequest request) throws Exception {
        BaseApiService baseApiService = apiServiceFactory.getService(Constants.PLATFORM_PDD);
        baseApiService.searchGoods(request);
        return new ArrayList<>();
    }
}
