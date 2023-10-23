package com.lqjk.third.service.impl;

import com.lqjk.third.beans.*;
import com.lqjk.third.mapper.CommunityTaskProcessMapper;
import com.lqjk.third.mapper.CommunityTaskRecordMapper;
import com.lqjk.third.mapper.CommunityTaskRecordThirdMapper;
import com.lqjk.third.mapper.EnergyFrontDataMapper;
import com.lqjk.third.service.AppThirdAuthService;
import com.lqjk.third.service.EnergyFrontDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class EnergyFrontDataServiceImpl implements EnergyFrontDataService {

    @Autowired
    private AppThirdAuthService appThirdAuthService;

    @Autowired
    private EnergyFrontDataMapper energyFrontDataMapper;
    @Autowired
    private CommunityTaskProcessMapper communityTaskProcessMapper;
    @Autowired
    private CommunityTaskRecordMapper communityTaskRecordMapper;
    @Autowired
    private CommunityTaskRecordThirdMapper communityTaskRecordThirdMapper;

    @Override
    @Transactional
    public void insertEnergyFrontData(EnergyFrontData energyFrontData) {
        log.info("--------------------开始保存能量前置表------------------------------ ｛｝", energyFrontData);
        AppThirdAuth authQuery = new AppThirdAuth();
        authQuery.setOwnerId(energyFrontData.getOwnerId());
        authQuery.setAuthType("01");
        boolean authUserInfoFlag = appThirdAuthService.checkAuth(authQuery);
        if (authUserInfoFlag) {
            //授权地铁H5产生
            energyFrontData.setRemark("1");
        }
        energyFrontDataMapper.insert(energyFrontData);
        log.info("--------------------开始修改任务表------------------------------");
        CommunityTaskRecordThird communityTaskRecord = energyFrontData.getCommunityTaskRecord();
        int completeTimes = communityTaskRecord.getCompleteTimes();
        communityTaskRecord.setCompleteTimes(completeTimes + 1);
        BigDecimal totalEnergy = communityTaskRecord.getTotalEnergy();
        communityTaskRecord.setTotalEnergy(totalEnergy.add(energyFrontData.getEnergyValue()));
        communityTaskRecord.setRealTotalEnergy(totalEnergy.add(energyFrontData.getEnergyValue()));
        int taskParam = communityTaskRecord.getTaskParam();
        communityTaskRecord.setTaskParam((int) (taskParam + energyFrontData.getMileage()));
        communityTaskRecord.setUpdateTime(new Date());
        communityTaskRecordMapper.update(communityTaskRecord);
        communityTaskRecord.setThirdChannel(energyFrontData.getThirdChannel());
        communityTaskRecordThirdMapper.update(communityTaskRecord);
        log.info("--------------------开始保存任务明细表------------------------------");
        CommunityTaskProcess communityTaskProcess = new CommunityTaskProcess();
        communityTaskProcess.setTaskId(communityTaskRecord.getTaskId());
        communityTaskProcess.setProcessStatus("01");
        communityTaskProcess.setCreateTime(new Date());
        communityTaskProcess.setOwnerId(energyFrontData.getOwnerId());
        communityTaskProcess.setBeforeEnergy(totalEnergy);
        communityTaskProcess.setEnergy(energyFrontData.getEnergyValue());
        communityTaskProcess.setAppChannel(energyFrontData.getAppChannel());
        communityTaskProcess.setUserNo(energyFrontData.getUserNo());
        communityTaskProcessMapper.insert(communityTaskProcess);
    }
}
