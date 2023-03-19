package com.zacboot.api.mini.service.subapp;

import com.alibaba.fastjson.JSON;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.*;
import com.pdd.pop.sdk.http.api.pop.response.*;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import com.zacboot.api.mini.beans.Constants;
import com.zacboot.api.mini.beans.reponses.SearchResponse;
import com.zacboot.api.mini.beans.requests.SearchRequest;
import com.zacboot.api.mini.service.BaseApiService;
import com.zacboot.common.base.constants.RedisKey;
import com.zacboot.common.base.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拼多多，多多客推广
 */
@Slf4j
@Service("pdd")
public class PddApiServiceImpl implements BaseApiService {

    @Value("${api.pdd.clientId}")
    private String clientId;
    @Value("${api.pdd.clientSecret}")
    private String clientSecret;

    @Value("${api.pdd.callBackUrl}")
    private String callBackUrl;

    private PopAccessTokenClient accessTokenClient;
    private PopHttpClient httpClient;

    @Autowired
    private RedisUtil redisUtil;

    private String accessToken;

    @PostConstruct
    public void initClient() {
        try {
            httpClient = new PopHttpClient(clientId, clientSecret);
            accessTokenClient = new PopAccessTokenClient(clientId, clientSecret);
        } catch (Exception e) {
            log.error("初始化PDD请求异常", e);
        }
    }

    @Override
    public SearchResponse searchGoods(SearchRequest request) throws Exception {
        List<String> pids = generatePid();
        generateAuthorityUrls(pids);

        // 5. 根据关键字搜索推广商品
        PddDdkGoodsSearchRequest pddDdkGoodsSearchRequest = new PddDdkGoodsSearchRequest();
        pddDdkGoodsSearchRequest.setKeyword(request.getGoodsName());
        pddDdkGoodsSearchRequest.setPid(pids.get(0));
        pddDdkGoodsSearchRequest.setCustomParameters("{\"new\":1}");
        List<PddDdkGoodsSearchRequest.RangeListItem> rangeList = new ArrayList<>();
        PddDdkGoodsSearchRequest.RangeListItem item = new PddDdkGoodsSearchRequest.RangeListItem();
        item.setRangeFrom(1000L);
        item.setRangeId(6);
        item.setRangeTo(10000L);
        rangeList.add(item);
        pddDdkGoodsSearchRequest.setRangeList(rangeList);
        PddDdkGoodsSearchResponse httpResponse = httpClient.syncInvoke(pddDdkGoodsSearchRequest);
        log.info(JSON.toJSONString(httpResponse));

        generateAuthUrl(pids.get(0), httpResponse.getGoodsSearchResponse().getGoodsList().stream().map(PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem::getGoodsSign).collect(Collectors.toList()));
        return new SearchResponse();
    }

    private void generateAuthorityUrls(List<String> pidList) throws Exception {
        // 4. 生成营销工具推广链接
        PddDdkRpPromUrlGenerateRequest promotionUrlGenerateRequest = new PddDdkRpPromUrlGenerateRequest();
        promotionUrlGenerateRequest.setChannelType(10);
        promotionUrlGenerateRequest.setPIdList(pidList);
        PddDdkRpPromUrlGenerateResponse promotionUrlGenerateResponse = httpClient.syncInvoke(promotionUrlGenerateRequest, accessToken);
        log.info(JSON.toJSONString(promotionUrlGenerateResponse));
    }

    private void generateAuthUrl(String pid, List<String> goodsSignList) throws Exception {
        // 生成普通商品推广链接
        PddDdkGoodsPromotionUrlGenerateRequest pddDdkGoodsPromotionUrlGenerateRequest = new PddDdkGoodsPromotionUrlGenerateRequest();
        pddDdkGoodsPromotionUrlGenerateRequest.setPId(pid);
        pddDdkGoodsPromotionUrlGenerateRequest.setCustomParameters("{\"new\":1}");
        pddDdkGoodsPromotionUrlGenerateRequest.setGoodsSignList(goodsSignList);
        PddDdkGoodsPromotionUrlGenerateResponse promotionUrlGenerateResponse = httpClient.syncInvoke(pddDdkGoodsPromotionUrlGenerateRequest);
        log.info(JSON.toJSONString(promotionUrlGenerateResponse));
    }

    /**
     * 3. 获取推广位pid列表
     *
     * @return
     * @throws Exception
     */
    private List<String> generatePid() throws Exception {
        // 查询已经存在的推广位
        PddDdkGoodsPidQueryRequest pddDdkGoodsPidQueryRequest = new PddDdkGoodsPidQueryRequest();
        pddDdkGoodsPidQueryRequest.setStatus(0);
        pddDdkGoodsPidQueryRequest.setPage(0);
        pddDdkGoodsPidQueryRequest.setPageSize(10);
        PddDdkGoodsPidQueryResponse response = httpClient.syncInvoke(pddDdkGoodsPidQueryRequest);
        if (null == response.getErrorResponse() && response.getPIdQueryResponse().getTotalCount() > 0) {
            return response.getPIdQueryResponse().getPIdList().stream().map(PddDdkGoodsPidQueryResponse.PIdQueryResponsePIdListItem::getPId).toList();
        }
        PddDdkGoodsPidGenerateRequest pidGenerateRequest = new PddDdkGoodsPidGenerateRequest();
        // 推广位数量
        pidGenerateRequest.setNumber(10L);
        PddDdkGoodsPidGenerateResponse pidGenerateResponse = httpClient.syncInvoke(pidGenerateRequest, accessToken);
        log.info(JSON.toJSONString(pidGenerateResponse));
        return pidGenerateResponse.getPIdGenerateResponse().getPIdList().stream().map(PddDdkGoodsPidGenerateResponse.PIdGenerateResponsePIdListItem::getPId).toList();
    }

    @Override
    public String getToken(String code) throws Exception {
        if (StringUtils.isNotBlank(code)) {
            // 2. 获取token
            AccessTokenResponse tokenResponse = accessTokenClient.generate(code);
            log.info("tokenResponse = " + JSON.toJSONString(tokenResponse));
            accessToken = tokenResponse.getAccessToken();
            redisUtil.set(RedisKey.PDD_DDK_TOKEN, accessToken, tokenResponse.getExpiresIn());
            return accessToken;
        }

        Object redisToken = redisUtil.get(RedisKey.PDD_DDK_TOKEN);
        if (null == redisToken) {
            // 1：拼接推广者授权链接，获取code
            String codeUrl = "https://jinbao.pinduoduo.com/open.html?response_type=code&client_id=" + clientId + "&redirect_uri=" + callBackUrl;
            log.info("code auth url: {}", codeUrl);
            return codeUrl;
        } else {
            accessToken = redisToken.toString();
        }
        return accessToken;
    }

    @Override
    public String getByName(String platform) {
        return Constants.PLATFORM_PDD;
    }
}
