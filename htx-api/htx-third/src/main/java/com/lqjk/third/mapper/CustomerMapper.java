package com.lqjk.third.mapper;

import com.lqjk.base.bizentity.SysUser;
import com.lqjk.third.beans.CustomerLogin;
import com.lqjk.third.beans.UserInformation;
import com.lqjk.third.beans.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerMapper {

    CustomerLogin getCustomerInfoByPhone(@Param("phone") String phone);

    int insertUser(SysUser entity);

    int insertCustomer(UserLogin userLogin);

    int insertUserInformation(UserInformation entity);

    UserInformation selectUserInformationByOwnerId(Long ownerId);
}
