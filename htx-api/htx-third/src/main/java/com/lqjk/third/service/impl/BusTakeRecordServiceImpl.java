package com.lqjk.third.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.lqjk.third.beans.*;
import com.lqjk.third.mapper.BusTakeRecordMapper;
import com.lqjk.third.mapper.CommunityTaskMapper;
import com.lqjk.third.mapper.CommunityTaskRecordMapper;
import com.lqjk.third.service.BusTakeRecordService;
import com.lqjk.third.service.ChainService;
import com.lqjk.third.service.EnergyFrontDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName BusTakeRecordServiceImpl
 * @Description TODO
 * @Author YJD
 * @Date 2023/3/6 11:19
 * @Version 1.0
 */
@Slf4j
@Service
public class BusTakeRecordServiceImpl implements BusTakeRecordService {

    @Autowired
    private BusTakeRecordMapper busTakeRecordMapper;

    @Autowired
    private CommunityTaskRecordMapper communityTaskRecordMapper;

    @Autowired
    private TeamRecordService teamRecordService;

    @Autowired
    private EnergyFrontDataService energyFrontDataService;

    @Value("${carbon.busTaskId}")
    private String busTaskId;
    @Autowired
    private CommunityTaskMapper taskMapper;

    @Autowired
    private ChainService chainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadBusInfo(BusTakeRecord busTakeRecord) {
        String orderNo = busTakeRecord.getTransOrderNo();
        log.info("订单号：" + orderNo);
        if (StringUtils.isNotEmpty(orderNo)) {
            if (busTakeRecordMapper.load(orderNo) != null) {
                log.info("订单号：" + orderNo + "以上链 退出");
                return;
            }
        }
        String taskId = busTaskId;
        if(ThirdChannelEnum.HTX_APP.getCode().equals(busTakeRecord.getAppChannel())){
            taskId = taskId + "_"+ThirdChannelEnum.HTX_APP.getCode();
        }
        CommunityTask communityTask = taskMapper.load(taskId, busTakeRecord.getAppChannel());
        if (communityTask == null) {
            log.info("任务配置信息未找到 退出");
            return;
        }
        CommunityTaskRecord communityTaskRecord = communityTaskRecordMapper.selectOfUserIdAndTaskId(busTakeRecord.getUserId(), taskId);
        if (communityTaskRecord == null) {
            log.info("该账号未领取乘公交任务 退出");
            return;
        }
        BigDecimal RC = communityTask.getTaskReward();
        BigDecimal PZRC = RC;
        log.info("计算得到减碳量：" + RC + "g");
        String img = null;
        log.info("--------------------计算乘公交减排量------------------------------");
        log.info("计算得到膨胀减碳量：" + PZRC + "g");
        log.info("--------------------开始保存公交行程表------------------------------");
        busTakeRecord.setStatus("01");
        busTakeRecord.setCreateTime(new Date());
        busTakeRecord.setUpdateTime(new Date());
        busTakeRecord.setCity("0532");
        busTakeRecord.setCarbon(RC);
        try {
            busTakeRecord.setTeamNo(teamRecordService.queryUserAndInsert(busTakeRecord.getUserId(), 2, RC, DateUtil.parse(busTakeRecord.getTransDate() + busTakeRecord.getTransTime())));
        } catch (Exception e) {
            log.error("获取{}的企业组队信息失败失败", busTakeRecord.getUserId());
        }
        JSONObject response = chainService.onBusChain(busTakeRecord);
        log.info("--------------------开始保存公交行程表------------------------------");
        String longevityChainFlag = null;
        //上链成功
        if (response != null && "0".equals(response.getStr("code"))) {
            longevityChainFlag = "03";
        } else {
            longevityChainFlag = "04";
        }
        busTakeRecord.setLongevityChainFlag(longevityChainFlag);
        busTakeRecordMapper.insert(busTakeRecord);
        if("03".equals(longevityChainFlag)){
            EnergyFrontData energyFrontDataVo = new EnergyFrontData();
            energyFrontDataVo.setOwnerId(busTakeRecord.getUserId());
            energyFrontDataVo.setEnergyValue(RC);
            energyFrontDataVo.setMileage(communityTask.getTaskReward().longValue());
            energyFrontDataVo.setAppChannel(busTakeRecord.getAppChannel());
            energyFrontDataVo.setUserNo(busTakeRecord.getUserNo());
            energyFrontDataVo.setCommunityTaskRecord((CommunityTaskRecordThird) communityTaskRecord);
            energyFrontDataVo.setThirdChannel(busTakeRecord.getThirdChannel());
            energyFrontDataVo.setEnergyScene(ScenesType.BUS.getCode());
            energyFrontDataVo.setEnergySource(ScenesType.BUS.getTypeName());
            energyFrontDataVo.setTransOrderNo(orderNo);
            energyFrontDataService.insertEnergyFrontData(energyFrontDataVo);
        }
    }

    @Override
    public Long sumCarbon(Long ownerId,String source) {
        return busTakeRecordMapper.sumCarbon(ownerId,source);
    }

}
