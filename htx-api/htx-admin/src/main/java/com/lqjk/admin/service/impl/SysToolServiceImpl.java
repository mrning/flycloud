package com.lqjk.admin.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson2.JSONObject;
import com.lqjk.admin.service.SysToolService;
import com.lqjk.base.utils.DateUtil;
import com.lqjk.base.utils.DingUtils;
import com.lqjk.base.utils.RedisUtil;
import com.lqjk.base.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.oer.its.etsi102941.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysToolServiceImpl implements SysToolService {

    // 【生态环境局-决策公开】
    public static final String STHJJ_NEWS = "https://sthj.sh.gov.cn/hbzhywpt2022/index.html";
    // 【环境能源交易所-新闻中心】
    public static final String HJS_NEW = "https://www.cneeex.com/xwzx/tzgg/";
    //【上海市一网通办新闻筛选-关键词=碳普惠】
    public static final String YWTB_NEWS = "https://search.sh.gov.cn/searchResult";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String checkNews() {
        String result = "";
        try {
            String sthjjRes = RestUtil.getHtml(STHJJ_NEWS);
            List<String> sthjjList = ReUtil.findAll("""
                    <ul style="display: block;">(.*?)</ul>""", sthjjRes, 0);
            List<String> sthjjTitles = ReUtil.findAll("title=\"(.*?)\"", sthjjList.get(0), 1);
            List<String> sthjjHrefs = ReUtil.findAll("href=\"(.*?)\"", sthjjList.get(0), 1);
            List<String> sthjjDates = ReUtil.findAll("<span class=\"date\">(.*?)</span>", sthjjList.get(0), 1);
            List<Object> cache = redisUtil.lGet("htx:news:STHJJ_NEWS", 0, 10L);
            Set<String> cacheStr = (!CollectionUtils.isEmpty(cache) ? (Set<String>) cache.get(0) : new HashSet<>());
            List<String> titles = sthjjTitles;
            titles.removeAll(cacheStr);
            if (!CollectionUtils.isEmpty(titles)) {
                for (int i = 0; i < titles.size(); i++) {
                    DingUtils.sendLinkMsg(DateUtil.formatDate() + "【上海市生态环境局】最新新闻 \n" + sthjjTitles.get(i),
                            "【" + sthjjDates.get(i) + "】" + sthjjTitles.get(i),
                            "https://sthj.sh.gov.cn" + sthjjHrefs.get(i));
                }
                cacheStr.addAll(titles);
                redisUtil.lSet("htx:news:STHJJ_NEWS", cacheStr, -1);
            }
            result = result + "当前【生态环境局】无新增新闻，";
        } catch (Exception e) {
            log.error("生态环境局-决策公开 error", e);
        }


        try {
            String hjsRes = RestUtil.getHtml(HJS_NEW);
            List<String> hjsList = ReUtil.findAll("""
                    <ul class="list-unstyled articel-list">(.*?)</ul>""", hjsRes, 0);
            List<String> hjsFilter = ReUtil.findAll("""
                    <li class="text-ellipsis  hidden-xs">(.*?)</li>""", hjsList.get(0), 0);

            List<String> hjsTitles = new ArrayList<>();
            List<String> hjsHrefs = new ArrayList<>();
            List<String> hjsDates = new ArrayList<>();
            for (String s : hjsFilter) {
                s = s.replaceAll("&#32;", "");
                hjsTitles.add(ReUtil.findAll("target=\"_blank\">(.*?)</a></li>", s, 1).get(0));
                hjsHrefs.add(ReUtil.findAll("href=\"(.*?)\"", s, 1).get(0));
                hjsDates.add(ReUtil.findAll("<span class=\"pull-right m-l-sm\">(.*?)</span>", s, 1).get(0));
            }
            List<Object> hjsCache = redisUtil.lGet("htx:news:HJS_NEWS", 0, 15L);
            Set<String> hjsCacheStr = (!CollectionUtils.isEmpty(hjsCache) ? (Set<String>) hjsCache.get(0) : new HashSet<>());
            List<String> titles = hjsTitles;
            titles.removeAll(hjsCacheStr);
            if (!CollectionUtils.isEmpty(titles)) {
                for (int i = 0; i < titles.size(); i++) {
                    DingUtils.sendLinkMsg(DateUtil.formatDate() + "【上海市环境能源交易所】最新新闻 \n" + hjsTitles.get(i),
                            "【" + hjsDates.get(i) + "】" + hjsTitles.get(i) +", url = https://ww.cneeex.com" + hjsHrefs.get(i),
                            "https://www.cneeex.com" + hjsHrefs.get(i));
                }
                hjsCacheStr.addAll(titles);
                redisUtil.lSet("htx:news:HJS_NEWS", hjsCacheStr, -1);
            }
            result = result + "当前【环境能源交易所】无新增要闻";
        } catch (Exception e) {
            log.error("环境能源交易所-新闻中心 error", e);
        }
        return result;
    }
}
