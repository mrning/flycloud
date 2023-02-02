package com.zacboot.api.sp.service.subapp;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pdd.pop.sdk.http.PopAccessTokenClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkOauthGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkRpPromUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPidGenerateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkOauthGoodsSearchResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkRpPromUrlGenerateResponse;
import com.pdd.pop.sdk.http.token.AccessTokenResponse;
import com.zacboot.api.sp.beans.reponses.SearchResponse;
import com.zacboot.api.sp.beans.requests.SearchRequest;
import com.zacboot.api.sp.service.BaseApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 拼多多，多多客推广
 */
@Slf4j
@Service
public class PddApiServiceImpl implements BaseApiService {

    @Value("${api.pdd.clientId}")
    private String clientId;
    @Value("${api.pdd.clientSecret}")
    private String clientSecret;

    @Value("${api.pdd.callBackUrl}")
    private String callBackUrl;

    private PopAccessTokenClient accessTokenClient;
    private PopHttpClient httpClient;

    private final String accessToken = "d11c7e0c35a14e2b972899b6488266846af4cf87";

    private String pid;

    @PostConstruct
    public void initClient(){
        httpClient = new PopHttpClient(clientId,clientSecret);
        accessTokenClient = new PopAccessTokenClient(clientId, clientSecret);
        try {
            // 1：拼接推广者授权链接，获取code
            String res = HttpUtil.get("https://jinbao.pinduoduo.com/open.html?response_type=code&client_id="+clientId+"&redirect_uri=" + callBackUrl);
//            accessToken = getToken("b58c2656a3534714b267a82987a017656c7680cb").getAccessToken();
            List<String> pids = generatePid();
            generateAuthorityUrls(pids);
            pid = pids.get(0);
            generateAuthUrl(pid);
        } catch (Exception e) {
            log.error("生成pdd授权备案链接失败",e);
        }
    }

    @Override
    public SearchResponse searchGoods(SearchRequest request) throws Exception {
        // 5. 根据关键字搜索推广商品
        PddDdkOauthGoodsSearchRequest pddDdkGoodsSearchRequest = new PddDdkOauthGoodsSearchRequest();
        pddDdkGoodsSearchRequest.setKeyword(request.getGoodsName());
        pddDdkGoodsSearchRequest.setPid(pid);
        PddDdkOauthGoodsSearchResponse httpResponse = httpClient.syncInvoke(pddDdkGoodsSearchRequest,accessToken);
        log.info(JSON.toJSONString(httpResponse));
        return new SearchResponse();
    }

    private void generateAuthorityUrls(List<String> pidList) throws Exception {
        // 4. 为生成的推广位(pid)生成授权链接，点击后进行授权备案，授权后才能正常使用推广位
        PddDdkRpPromUrlGenerateRequest promotionUrlGenerateRequest = new PddDdkRpPromUrlGenerateRequest();
        promotionUrlGenerateRequest.setChannelType(10);
        promotionUrlGenerateRequest.setPIdList(pidList);
        PddDdkRpPromUrlGenerateResponse promotionUrlGenerateResponse = httpClient.syncInvoke(promotionUrlGenerateRequest,accessToken);
        log.info(JSON.toJSONString(promotionUrlGenerateResponse));
    }

    private void generateAuthUrl(String pid) throws Exception {
        PddDdkGoodsPromotionUrlGenerateRequest pddDdkGoodsPromotionUrlGenerateRequest = new PddDdkGoodsPromotionUrlGenerateRequest();
        pddDdkGoodsPromotionUrlGenerateRequest.setPId(pid);
        PddDdkGoodsPromotionUrlGenerateResponse promotionUrlGenerateResponse = httpClient.syncInvoke(pddDdkGoodsPromotionUrlGenerateRequest);
        log.info(JSON.toJSONString(promotionUrlGenerateResponse));
    }

    /**
     * 3. 获取推广位pid列表
     * @return
     * @throws Exception
     */
    private List<String> generatePid() throws Exception {
        PddDdkGoodsPidGenerateRequest pidGenerateRequest = new PddDdkGoodsPidGenerateRequest();
        // 推广位数量
        pidGenerateRequest.setNumber(10L);
        PddDdkGoodsPidGenerateResponse pidGenerateResponse = httpClient.syncInvoke(pidGenerateRequest,accessToken);
        log.info(JSON.toJSONString(pidGenerateResponse));
        return pidGenerateResponse.getPIdGenerateResponse().getPIdList().stream().map(PddDdkGoodsPidGenerateResponse.PIdGenerateResponsePIdListItem::getPId).toList();
    }

    private AccessTokenResponse getToken(String code) throws Exception {
        // 2. 获取token
        AccessTokenResponse tokenResponse = accessTokenClient.generate(code);
        log.info("tokenResponse = "+ JSON.toJSONString(tokenResponse));
        return tokenResponse;
    }

    private AccessTokenResponse refreshToken(String refreshToken) throws Exception {
        return accessTokenClient.refresh(refreshToken);
    }
}
