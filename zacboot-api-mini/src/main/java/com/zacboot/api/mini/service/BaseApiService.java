package com.zacboot.api.mini.service;

import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;

import java.util.List;

public interface BaseApiService {

    List<SearchResponse> searchGoods(SearchRequest request) throws Exception;

    String getToken(String code) throws Exception;

    String getByName(String platform);
}
