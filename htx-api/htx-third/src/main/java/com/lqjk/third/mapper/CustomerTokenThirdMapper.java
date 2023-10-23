package com.lqjk.third.mapper;

import com.lqjk.third.beans.CustomerTokenThird;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
@Mapper
public interface CustomerTokenThirdMapper {

    CustomerTokenThird selectById(Long id);

    List<CustomerTokenThird> selectByCustomerTokenThird(CustomerTokenThird customerTokenThird);

    int insert(CustomerTokenThird customerTokenThird);

    int update(CustomerTokenThird customerTokenThird);

    int deleteById(Long id);

    CustomerTokenThird selectByChannelAndToken(@Param("thirdChannel") String thirdChannel, @Param("token") String token);

    CustomerTokenThird selectByOwnerId(@Param("ownerId") Long ownerId);

    int deleteCustomerTokenByOwnerId(@Param("thirdChannel") String thirdChannel, @Param("token") String token);
}
