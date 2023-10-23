package com.lqjk.third.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqjk.third.beans.TeamRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamRecordMapper extends BaseMapper<TeamRecord> {

    int insert(TeamRecord record);


    int updateByPrimaryKeySelective(TeamRecord record);

}