package com.lqjk.third.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lqjk.mq.constants.MQConstant;
import com.lqjk.mq.service.RabbitMqService;
import com.lqjk.third.beans.TeamCompanyUser;
import com.lqjk.third.beans.TeamRecord;
import com.lqjk.third.mapper.TeamRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 队伍记录相关表
 */
@Service
@Slf4j
public class TeamRecordService {

    @Autowired
    private TeamCompanyUserService teamCompanyUserService;
    @Autowired
    private TeamRecordMapper teamRecordMapper;
    @Autowired
    private RabbitMqService rabbitMqService;


    public Long queryUserAndInsert(Long ownerId, Integer travelType, BigDecimal carbon,Date joinTime){
        List<TeamCompanyUser> teamCompanyUsers = teamCompanyUserService.queryTeamInfoByUserId(ownerId,joinTime);
        log.info("查询用户信息为{}", JSONUtil.toJsonStr(teamCompanyUsers));
        if(CollUtil.isEmpty(teamCompanyUsers)){
            return 0L;
        }
        // 对碳值进行累加
        Long comId = teamCompanyUsers.get(0).getComId();
        if(comId == null){
            return comId;
        }
        insertTeamRecord(ownerId, comId, travelType, carbon);
        return comId;
    }

    /**
     * 增加新的出行记录入口
     * @param ownerId
     * @param comId
     * @param travelType
     * @param carbon
     */
    public void insertTeamRecord(Long  ownerId, Long comId, Integer travelType, BigDecimal carbon){
        List<TeamRecord> teamRecords = queryRecordByType(ownerId, comId, travelType);
        // 查询是否有记录
        if(CollUtil.isEmpty(teamRecords)){
            // 创建记录
            createRecord(ownerId, comId, travelType, carbon);
        }else{
            // 更新记录
            updateRecord(teamRecords.get(0), carbon);
        }
        Map<String, Long> param = new HashMap<>();
        param.put("comId", comId);
        param.put("carbon", carbon.longValue());
        rabbitMqService.sendObj(MQConstant.COMPANY_CARBON_ADD, param);
    }

    /**
     * 更新碳值和次数
     * @param teamRecord
     * @param carbon
     * @return
     */
    private int updateRecord(TeamRecord teamRecord, BigDecimal carbon){
        teamRecord.setTravelTimes(teamRecord.getTravelTimes() + 1);
        teamRecord.setTravalCarbon(teamRecord.getTravalCarbon().add(carbon));
        teamRecord.setLastUpdate(new Date());
        return teamRecordMapper.updateByPrimaryKeySelective(teamRecord);
    }

    /**
     * 插入新的出行记录
     * @param ownerId
     * @param comId
     * @param travelType
     * @param carbon
     * @return
     */
    private int createRecord(Long ownerId, Long comId, Integer travelType, BigDecimal carbon){
        TeamRecord teamRecord = new TeamRecord();
        teamRecord.setOwnerId(ownerId);
        teamRecord.setStatus(1);
        teamRecord.setComId(comId);
        teamRecord.setTravelType(travelType);
        teamRecord.setLastUpdate(new Date());
        teamRecord.setTravelTimes(1);
        teamRecord.setTravalCarbon(carbon);
        return teamRecordMapper.insert(teamRecord);
    }

    /**
     * 根据出行类型查询记录
     * @param ownerId
     * @param comId
     * @param travelType
     * @return
     */
    private List<TeamRecord> queryRecordByType(Long  ownerId, Long comId, Integer travelType){
        return teamRecordMapper.selectList(Wrappers.lambdaQuery(TeamRecord.class)
                .eq(TeamRecord::getOwnerId,ownerId)
                .eq(TeamRecord::getComId, comId)
                .eq(TeamRecord::getTravelType, travelType)
                .eq(TeamRecord::getStatus, 1));
    }

}
