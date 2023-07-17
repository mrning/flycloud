package com.zacboot.api.mini.service.subapp;

import com.zacboot.api.mini.beans.PlatformEnum;
import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;
import com.zacboot.api.mini.service.BaseApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 淘宝联盟 - 开放平台api
 */
@Slf4j
@Service
public class TbApiServiceImpl implements BaseApiService {

    @Override
    public List<SearchResponse> searchGoods(SearchRequest request) throws Exception {
        return null;
    }

    @Override
    public String getToken(String code) throws Exception {
        return null;
    }

    @Override
    public String getByName(String platform) {
        return PlatformEnum.PLATFORM_TB.getName();
    }
}
