package com.lqjk.admin.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson2.JSONObject;
import com.lqjk.admin.service.SysToolService;
import com.lqjk.base.utils.DateUtil;
import com.lqjk.base.utils.DingUtils;
import com.lqjk.base.utils.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysToolServiceImpl implements SysToolService {

    // 【生态环境局-决策公开】
    public static final String STHJJ_NEWS = "https://sthj.sh.gov.cn/hbzhywpt2022/index.html";
    // 【环境能源交易所-新闻中心】
    public static final String HJS_NEW = "https://www.cneeex.com/xwzx/tzgg/";
    //【上海市一网通办新闻筛选-关键词=碳普惠】
    public static final String YWTB_NEWS = "https://search.sh.gov.cn/searchResult";
    @Override
    public String checkNews() {
        String sthjjRes = RestUtil.getHtml(STHJJ_NEWS);
        List<String> sthjjList = ReUtil.findAll("""
                <ul style="display: block;">(.*?)</ul>""", sthjjRes, 0);
        List<String> sthjjTitles = ReUtil.findAll("title=\"(.*?)\"", sthjjList.get(0), 0);
        List<String> sthjjHrefs = ReUtil.findAll("href=\"(.*?)\"", sthjjList.get(0), 0);
        List<String> sthjjDates = ReUtil.findAll("<span class=\"date\">(.*?)</span>", sthjjList.get(0), 0);
        DingUtils.sendLinkMsg(DateUtil.formatDate()+"【上海市生态环境局】最新新闻 \n"+sthjjTitles.get(0),"【"+sthjjDates.get(0)+"】"+sthjjTitles.get(0),
                sthjjHrefs.get(0));

        String hjsRes = RestUtil.getHtml(HJS_NEW);
        List<String> hjsList = ReUtil.findAll("""
                <ul class="list-unstyled articel-list">(.*?)</ul>""", hjsRes, 0);
        List<String> hjsFilter = ReUtil.findAll("""
                <li class="text-ellipsis  hidden-xs">(.*?)</li>""",hjsList.get(0),0);

        List<String> hjsTitles = new ArrayList<>();
        List<String> hjsHrefs = new ArrayList<>();
        List<String> hjsDates = new ArrayList<>();
        for (String s : hjsFilter){
            hjsTitles.add(ReUtil.findAll("target=\"_blank\">(.*?)</a></li>", s, 0).get(0));
            hjsHrefs.add(ReUtil.findAll("href=\"(.*?)\"", s, 0).get(0));
            hjsDates.add(ReUtil.findAll("<span class=\"pull-right m-l-sm\">(.*?)</span>", s, 0).get(0));
        }
        DingUtils.sendLinkMsg(DateUtil.formatDate()+"【上海市环境能源交易所】最新新闻 \n"+hjsTitles.get(0),"【"+hjsDates.get(0)+"】"+hjsTitles.get(0),
                hjsHrefs.get(0));
        return null;
    }
}
