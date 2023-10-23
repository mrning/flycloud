package com.lqjk.third.mapper;

import com.lqjk.third.beans.EnergyFrontData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnergyFrontDataMapper {

    void insert(EnergyFrontData energyFrontData);

    void update(EnergyFrontData energyFrontData);
   }
