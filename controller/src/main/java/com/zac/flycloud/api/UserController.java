package com.zac.flycloud.api;

import com.zac.flycloud.basebean.DataResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zac
 * @Date 20200702
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @ApiOperation("查询用户列表")
    @PostMapping(value = "/list")
    public DataResponseResult list(){
        return DataResponseResult.success();
    }


}
