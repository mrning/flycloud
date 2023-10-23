package com.lqjk.third.service.impl;

import cn.hutool.json.JSONObject;
import com.lqjk.third.beans.*;
import com.lqjk.third.mapper.*;
import com.lqjk.third.service.ChainService;
import com.lqjk.third.service.EnergyFrontDataService;
import com.lqjk.third.service.MetroTakeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @Description TODO
 * @Author YJD
 * @Date 2023/4/10 15:04
 * @Version 1.0
 */
@Slf4j
@Service
public class MetroTakeRecordServiceImpl implements MetroTakeRecordService {

    @Autowired
    private MetroTakeRecordMapper metroTakeRecordMapper;

    @Value("${carbon.metroTaskId}")
    private String metroTaskId;


    @Autowired
    private ChainService chainService;

    @Autowired
    private CommunityTaskMapper taskMapper;

    @Autowired
    private CommunityTaskRecordMapper communityTaskRecordMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private StationDistanceMapper stationDistanceMapper;

    @Autowired
    private TeamRecordService teamRecordService;

    @Autowired
    private EnergyFrontDataService energyFrontDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadMetroInfo(MetroTakeRecord metroTakeRecord) {
        try {
            String orderNo = metroTakeRecord.getTransOrderNo();
            log.info("订单号：" + orderNo);
            if (StringUtils.isNotBlank(orderNo)) {
                if (metroTakeRecordMapper.load(orderNo) != null) {
                    log.info("订单号：" + orderNo + "以上链 退出");
                    return;
                }
            } else {
                log.info("订单号不能为空");
                return;
            }
            String taskId = metroTaskId;
            if (ThirdChannelEnum.HTX_APP.getCode().equals(metroTakeRecord.getAppChannel())) {
                taskId = taskId + "_" + ThirdChannelEnum.HTX_APP.getCode();
            }
            CommunityTask communityTask = taskMapper.load(taskId, metroTakeRecord.getAppChannel());
            if (communityTask == null) {
                log.info("任务配置信息未找到 退出");
                return;
            }
            CommunityTaskRecord communityTaskRecord = communityTaskRecordMapper.selectOfUserIdAndTaskId(metroTakeRecord.getUserId(), taskId);
            if (communityTaskRecord == null) {
                log.info("该账号未领取乘地铁任务 退出");
                return;
            }
            BigDecimal LC;
            BigDecimal mileage = metroTakeRecord.getTransMileage();
            if (mileage != null && mileage.intValue() > 0) {
                LC = mileage;
            } else {
                log.info("--------------------开始根据站点计算公里数------------------------------");
                String inStationCode = "";
                String outStationCode = "";
                if (metroTakeRecord.getStationFalg()) {
                    inStationCode = metroTakeRecord.getEntryStation();
                    outStationCode = metroTakeRecord.getExitStation();
                } else {
                    Station inStation = stationMapper.queryStationByNameOne(metroTakeRecord.getEntryStation());
                    Station outStation = stationMapper.queryStationByNameOne(metroTakeRecord.getExitStation());
                    if (inStation == null || outStation == null) {
                        log.info("进出站未找到 退出");
                        return;
                    }
                    inStationCode = inStation.getItpCode();
                    outStationCode = outStation.getItpCode();
                }
                StationDistance stationDistance = stationDistanceMapper.load(inStationCode, outStationCode);
                if (stationDistance == null) {
                    log.info("计算距离出错 退出");
                    return;
                }
                LC = new BigDecimal(stationDistance.getStationDistance());
                metroTakeRecord.setTransMileage(LC);
            }
            log.info("计算得到公里数：" + LC + "m");
            BigDecimal RC = LC.multiply(communityTask.getTaskReward()).divide(new BigDecimal(1000)).setScale(0, RoundingMode.DOWN);
            log.info("计算得到减碳量：" + RC + "g");
            // 2022年11月3日 新增团组
            try {
                metroTakeRecord.setTeamNo(teamRecordService.queryUserAndInsert(metroTakeRecord.getUserId(), 1, RC, metroTakeRecord.getExitDate()));
            } catch (Exception e) {
                log.error("地铁：获取{}的企业组队信息失败", metroTakeRecord.getUserId());
            }
            JSONObject response = chainService.onMetroChain(metroTakeRecord, LC);
            log.info("--------------------开始保存地铁行程表------------------------------");
            String longevityChainFlag = null;
            //上链成功
            if (response != null && "0".equals(response.getStr("code"))) {
                longevityChainFlag = "03";
            } else {
                longevityChainFlag = "04";
            }
            metroTakeRecord.setStatus("01");
            metroTakeRecord.setCreateTime(new Date());
            metroTakeRecord.setUpdateTime(new Date());
            metroTakeRecord.setCarbon(RC);
            metroTakeRecord.setLongevityChainFlag(longevityChainFlag);
            metroTakeRecordMapper.insert(metroTakeRecord);
            if ("03".equals(longevityChainFlag)) {
                EnergyFrontData energyFrontDataVo = new EnergyFrontData();
                energyFrontDataVo.setOwnerId(metroTakeRecord.getUserId());
                energyFrontDataVo.setEnergyValue(RC);
                energyFrontDataVo.setMileage(LC.longValue());
                energyFrontDataVo.setAppChannel(metroTakeRecord.getAppChannel());
                energyFrontDataVo.setUserNo(metroTakeRecord.getUserNo());
                energyFrontDataVo.setCommunityTaskRecord((CommunityTaskRecordThird) communityTaskRecord);
                energyFrontDataVo.setThirdChannel(metroTakeRecord.getThirdChannel());
                energyFrontDataVo.setEnergyScene(ScenesType.METRO.getCode());
                energyFrontDataVo.setEnergySource(ScenesType.METRO.getTypeName());
                energyFrontDataVo.setTransOrderNo(orderNo);
                energyFrontDataService.insertEnergyFrontData(energyFrontDataVo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("上海交通卡地铁行程处理异常ownerId={}", metroTakeRecord.getUserId(), e);
        }
    }

    @Override
    public Long sumCarbon(Long ownerId, String source) {
        return metroTakeRecordMapper.sumCarbon(ownerId, source);
    }
}
