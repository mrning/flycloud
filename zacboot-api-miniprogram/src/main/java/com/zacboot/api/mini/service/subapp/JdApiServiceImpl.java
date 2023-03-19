package com.zacboot.api.mini.service.subapp;

import com.zacboot.api.mini.beans.Constants;
import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;
import com.zacboot.api.mini.service.BaseApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 京东联盟 - 开放平台api
 */
@Slf4j
@Service
public class JdApiServiceImpl implements BaseApiService {
    @Override
    public SearchResponse searchGoods(SearchRequest request) throws Exception {
        return null;
    }

    @Override
    public String getToken(String code) throws Exception {
        return null;
    }

    @Override
    public String getByName(String platform) {
        return Constants.PLATFORM_JINGDONG;
    }
}

