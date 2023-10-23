package com.lqjk.third.service.impl;

import com.lqjk.base.utils.RedisUtil;
import com.lqjk.third.beans.CustomerTokenThird;
import com.lqjk.third.mapper.CustomerTokenThirdMapper;
import com.lqjk.third.service.CustomerTokenThirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author libatou
 * @since 2023-01-30
 */
@Service
public class CustomerTokenThirdServiceImpl implements CustomerTokenThirdService {


    @Autowired
    private CustomerTokenThirdMapper customerTokenThirdMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Boolean deleteCustomerToken(String thirdChannel, String token) {
        redisUtil.del(thirdChannel+":"+token);
        return customerTokenThirdMapper.deleteCustomerTokenByOwnerId(thirdChannel,token) > 0;
    }

    @Override
    public CustomerTokenThird getByOwnerId(Long ownerId) {
        return customerTokenThirdMapper.selectByOwnerId(ownerId);
    }

    @Override
    public CustomerTokenThird selectByChannelAndToken(String thirdChannel, String token) {
        CustomerTokenThird customerTokenThird= (CustomerTokenThird) redisUtil.get(thirdChannel+":"+token);
        if(customerTokenThird==null){
            customerTokenThird = customerTokenThirdMapper.selectByChannelAndToken(thirdChannel,token);
            redisUtil.set(thirdChannel+":"+token,customerTokenThird);
        }
        return customerTokenThird;
    }


}
