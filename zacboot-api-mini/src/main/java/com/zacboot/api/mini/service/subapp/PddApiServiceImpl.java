package com.zacboot.api.mini.service.subapp;

import cn.hutool.core.collection.CollectionUtil;
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
import java.util.Arrays;
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
    public List<SearchResponse> searchGoods(SearchRequest request) throws Exception {
        List<SearchResponse> result = new ArrayList<>();

        PddDdkGoodsPidQueryRequest pidQueryRequest = new PddDdkGoodsPidQueryRequest();
        //  推广位状态：0-正常，1-封禁
        pidQueryRequest.setStatus(0);
        pidQueryRequest.setPidList(List.of("36488634_269505021"));

        PddDdkGoodsPidQueryResponse pidQueryResponse = httpClient.syncInvoke(pidQueryRequest);

        List<String> pids;
        if (null != pidQueryResponse.getPIdQueryResponse() && CollectionUtil.isNotEmpty(pidQueryResponse.getPIdQueryResponse().getPIdList())){
            pids = pidQueryResponse.getPIdQueryResponse().getPIdList().stream().map(PddDdkGoodsPidQueryResponse.PIdQueryResponsePIdListItem::getPId).toList();
        }else{
            pids = generatePid();
        }

        for (String pid : pids) {
            // 5. 根据关键字搜索推广商品 https://open.pinduoduo.com/application/document/api?id=pdd.ddk.goods.search
            PddDdkGoodsSearchRequest pddDdkGoodsSearchRequest = new PddDdkGoodsSearchRequest();
            // 商品关键词，与opt_id字段选填一个或全部填写。可支持goods_id、拼多多链接（即拼多多app商详的链接）、进宝长链/短链（即为pdd.ddk.goods.promotion.url.generate接口生成的长短链）
            pddDdkGoodsSearchRequest.setKeyword(request.getGoodsInfo());
            // 推广位id
            pddDdkGoodsSearchRequest.setPid(pid);
            pddDdkGoodsSearchRequest.setCustomParameters("{\"new\":1}");
            // 3-旗舰店，4-专卖店，5-专营店
            pddDdkGoodsSearchRequest.setMerchantTypeList(Arrays.asList(3,4,5));
            // 筛选范围列表
            List<PddDdkGoodsSearchRequest.RangeListItem> rangeList = new ArrayList<>();
            PddDdkGoodsSearchRequest.RangeListItem item = new PddDdkGoodsSearchRequest.RangeListItem();
            // 6=佣金金额
            item.setRangeId(6);
            // 区间的开始值 10
            item.setRangeFrom(1000L);

            rangeList.add(item);
            pddDdkGoodsSearchRequest.setRangeList(rangeList);
            // 6-按销量降序;
            pddDdkGoodsSearchRequest.setSortType(6);
            PddDdkGoodsSearchResponse httpResponse = httpClient.syncInvoke(pddDdkGoodsSearchRequest);
            log.info(JSON.toJSONString(httpResponse));
            if (null != httpResponse.getGoodsSearchResponse() && CollectionUtil.isNotEmpty(httpResponse.getGoodsSearchResponse().getGoodsList())) {
                for (PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem goodsItem : httpResponse.getGoodsSearchResponse().getGoodsList()) {
                    result.add(SearchResponse.convertByPddSearchRes(goodsItem));
                }
            }


            PddDdkGoodsPromotionUrlGenerateResponse promotionUrlGenerateResponse = generateAuthUrl(pid,
                    httpResponse.getGoodsSearchResponse().getGoodsList().stream().map(PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem::getGoodsSign).collect(Collectors.toList()));
            if (null != promotionUrlGenerateResponse && null != promotionUrlGenerateResponse.getGoodsPromotionUrlGenerateResponse()
                    && CollectionUtil.isNotEmpty(promotionUrlGenerateResponse.getGoodsPromotionUrlGenerateResponse().getGoodsPromotionUrlList())) {
                for (int i = 0; i < promotionUrlGenerateResponse.getGoodsPromotionUrlGenerateResponse().getGoodsPromotionUrlList().size(); i++) {
                    result.get(i).setLinkUrl(promotionUrlGenerateResponse.getGoodsPromotionUrlGenerateResponse().getGoodsPromotionUrlList().get(i).getMobileShortUrl());
                }
            }
        }
        return result;
    }

    private PddDdkGoodsPromotionUrlGenerateResponse generateAuthUrl(String pid, List<String> goodsSignList) throws Exception {
        PddDdkMemberAuthorityQueryRequest queryRequest = new PddDdkMemberAuthorityQueryRequest();
        queryRequest.setPid(pid);
        PddDdkMemberAuthorityQueryResponse authorityQueryResponse = httpClient.syncInvoke(queryRequest);
        // 1-已绑定；0-未绑定
        if (null != authorityQueryResponse.getAuthorityQueryResponse() && authorityQueryResponse.getAuthorityQueryResponse().getBind() == 0) {
            // 6 多多进宝推广链接生成,生成普通商品推广链接  https://open.pinduoduo.com/application/document/api?id=pdd.ddk.goods.promotion.url.generate
            PddDdkGoodsPromotionUrlGenerateRequest pddDdkGoodsPromotionUrlGenerateRequest = new PddDdkGoodsPromotionUrlGenerateRequest();
            // 推广位ID
            pddDdkGoodsPromotionUrlGenerateRequest.setPId(pid);
            pddDdkGoodsPromotionUrlGenerateRequest.setCustomParameters("{\"new\":1}");
            pddDdkGoodsPromotionUrlGenerateRequest.setGenerateAuthorityUrl(true);
            // 商品goodsSign列表
            pddDdkGoodsPromotionUrlGenerateRequest.setGoodsSignList(goodsSignList);
            PddDdkGoodsPromotionUrlGenerateResponse promotionUrlGenerateResponse = httpClient.syncInvoke(pddDdkGoodsPromotionUrlGenerateRequest);
            log.info(JSON.toJSONString(promotionUrlGenerateResponse));
            return promotionUrlGenerateResponse;
        }
        return null;
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
        pidGenerateRequest.setNumber(1L);
        // 绑定媒体id
        pidGenerateRequest.setMediaId(9657514214L);
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
