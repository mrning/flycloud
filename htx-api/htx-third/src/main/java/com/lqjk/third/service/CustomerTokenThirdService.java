package com.lqjk.third.service;


import com.lqjk.third.beans.CustomerTokenThird;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
public interface CustomerTokenThirdService {

    Boolean deleteCustomerToken(String thirdChannel, String toke);

    CustomerTokenThird getByOwnerId(Long ownerId);

    CustomerTokenThird selectByChannelAndToken(String thirdChannel,String token);
}
