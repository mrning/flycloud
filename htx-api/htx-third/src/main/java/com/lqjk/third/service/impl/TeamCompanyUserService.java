package com.lqjk.third.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lqjk.third.beans.TeamCompanyUser;
import com.lqjk.third.mapper.TeamCompanyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeamCompanyUserService {

    @Autowired
    private TeamCompanyUserMapper teamCompanyUserMapper;

    public List<TeamCompanyUser> queryTeamInfoByUserId(Long ownerId, Date joinTime) {
        return teamCompanyUserMapper.selectList(Wrappers.lambdaQuery(TeamCompanyUser.class)
                .eq(TeamCompanyUser::getOwnerId, ownerId)
                .eq(TeamCompanyUser::getStatus, "0"));
    }
}
