package com.lqjk.third.mapper;


import com.lqjk.third.beans.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInformationMapper {
    void updateUserInformation(UserInformation entity);

    UserInformation findUserInformationById(@Param("id") long id);

    UserInformation findUserInformationByOwnerId(Long ownerId);

    UserInformation findUserInformationByPhone(@Param("phone") String phone, @Param("appChannel") String appChannel);

    List<UserInformation> findUserInformationByUserNoIsNUll();
}
