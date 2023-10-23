package com.lqjk.third.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lqjk.third.beans.BusTakeRecord;
import com.lqjk.third.beans.MetroTakeRecord;
import com.lqjk.third.beans.TaxiTakeRecord;
import com.lqjk.third.service.ChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ChainServiceImpl
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/28 9:19
 * @Version 1.0
 */
@Slf4j
@Service
public class ChainServiceImpl implements ChainService {

    @Value("${blockchain.chainUrlHtx}")
    private String chainUrlHtx;

    public static final String CHAIN_METRO_TRAVEL = "/carbonIntegral/metro";
    public static final String CHAIN_TAXI_TRAVEL = "/carbonIntegral/taxi";
    public static final String CHAIN_BUS_TRAVEL = "/carbonIntegral/bus";

    @Override
    public JSONObject onMetroChain(MetroTakeRecord metroTakeRecord, BigDecimal distance) {
        log.info("-------------开始对行程信息进行上链-----------");
        try {
            if (distance.compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("交通卡地铁行程上链前过滤，本次行程距离为0, transOrderNo = {}", metroTakeRecord.getTransOrderNo());
                return null;
            }
            Map<String, Object> carbonMap = new LinkedHashMap<>();
            carbonMap.put("userId", String.valueOf(metroTakeRecord.getUserId()));
            carbonMap.put("startTime", metroTakeRecord.getEntryDate().getTime());
            carbonMap.put("endTime", metroTakeRecord.getExitDate().getTime());
            carbonMap.put("transOrderNo", metroTakeRecord.getTransOrderNo());
            carbonMap.put("distance", distance.intValue());
            carbonMap.put("channel", metroTakeRecord.getThirdChannel());
            carbonMap.put("enterpriseId", metroTakeRecord.getTeamNo());
            log.info("地铁区块链返回参数:{}", JSONUtil.toJsonStr(carbonMap));
            String response = HttpUtil.post(chainUrlHtx + CHAIN_METRO_TRAVEL, JSONUtil.toJsonStr(carbonMap));
            log.info("地铁区块链返回信息:{}", response);
            return JSONUtil.parseObj(response);
        } catch (Exception e) {
            log.error("上链异常" + e.getMessage(), e);
            return null;
        }
    }


    @Override
    public JSONObject onBusChain(BusTakeRecord busTakeRecord) {
        log.info("-------------开始对行程信息进行上链-----------");
        try {
            Map<String, Object> carbonMap = new LinkedHashMap<>();
            carbonMap.put("userId", String.valueOf(busTakeRecord.getUserId()));
            carbonMap.put("startTime", DateUtil.parse(busTakeRecord.getTransDate() + busTakeRecord.getTransTime(), "yyyyMMddHHmmss").getTime());
            carbonMap.put("transOrderNo", busTakeRecord.getTransOrderNo());
            carbonMap.put("channel", busTakeRecord.getThirdChannel());
            carbonMap.put("enterpriseId", busTakeRecord.getTeamNo());
            log.info("公交区块链返回参数:{}", JSONUtil.toJsonStr(carbonMap));
            String response = HttpUtil.post(chainUrlHtx + CHAIN_BUS_TRAVEL, JSONUtil.toJsonStr(carbonMap));
            log.info("公交区块链返回信息:{}", response);
            return JSONUtil.parseObj(response);
        } catch (Exception e) {
            log.error("上链异常" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public JSONObject onTaxiChain(TaxiTakeRecord taxiTakeRecord, Long mileage) {
        log.info("-------------开始对出租行程信息进行上链-----------");
        try {
            Map<String, Object> carbonMap = new LinkedHashMap<>();
            carbonMap.put("userId", String.valueOf(taxiTakeRecord.getUserId()));
            carbonMap.put("startTime", taxiTakeRecord.getStartTime().getTime());
            carbonMap.put("endTime", taxiTakeRecord.getEndTime().getTime());
            carbonMap.put("transOrderNo", taxiTakeRecord.getTransOrderNo());
            carbonMap.put("distance", mileage);
            carbonMap.put("channel", taxiTakeRecord.getThirdChannel());
            carbonMap.put("enterpriseId", taxiTakeRecord.getTeamNo());
            log.info("地铁区块链返回参数:{}", JSONUtil.toJsonStr(carbonMap));
            String response = HttpUtil.post(chainUrlHtx + CHAIN_TAXI_TRAVEL, JSONUtil.toJsonStr(carbonMap));
            log.info("地铁区块链返回信息:{}", response);
            return JSONUtil.parseObj(response);
        } catch (Exception e) {
            log.error("出租上链异常" + e.getMessage(), e);
            return null;
        }
    }

}
