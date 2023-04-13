package com.zacboot.admin.feign;

import com.zac.system.core.response.weixin.QwUserVo;
import com.zacboot.common.base.basebeans.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "zacboot-admin-work", path = "/wx")
public interface WeixinApiFeign {

    @PostMapping("/getWxUsers")
    Result<List<QwUserVo>> getWxUsers();
}
