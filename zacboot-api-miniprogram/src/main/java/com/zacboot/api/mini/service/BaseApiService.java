package com.zacboot.api.mini.service;

import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;

public interface BaseApiService {

    SearchResponse searchGoods(SearchRequest request) throws Exception;

    String getToken(String code) throws Exception;

    String getByName(String platform);
}
