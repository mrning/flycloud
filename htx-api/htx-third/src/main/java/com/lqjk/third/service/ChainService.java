package com.lqjk.third.service;

import cn.hutool.json.JSONObject;
import com.lqjk.third.beans.BusTakeRecord;
import com.lqjk.third.beans.MetroTakeRecord;
import com.lqjk.third.beans.TaxiTakeRecord;

import java.math.BigDecimal;

/**
 * @ClassName ChainService
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/28 9:17
 * @Version 1.0
 */
public interface ChainService {

    JSONObject onMetroChain(MetroTakeRecord metroTakeRecord, BigDecimal distance);

    JSONObject onBusChain(BusTakeRecord busTakeRecord);

    JSONObject onTaxiChain(TaxiTakeRecord taxiTakeRecord, Long mileage);
}
