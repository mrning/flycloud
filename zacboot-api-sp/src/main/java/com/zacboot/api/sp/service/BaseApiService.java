package com.zacboot.api.sp.service;

import com.zacboot.api.sp.beans.reponses.SearchResponse;
import com.zacboot.api.sp.beans.requests.SearchRequest;

public interface BaseApiService {

    SearchResponse searchGoods(SearchRequest request) throws Exception;
}
