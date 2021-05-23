package com.zac.flycloud.api.admin;

import com.zac.flycloud.basebean.DataResponseResult;
import com.zac.flycloud.dto.SysUserDTO;
import com.zac.flycloud.service.feignservice.FSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fapi")
public class FUserController {

    @Autowired
    private FSysUserService fSysUserService;

    @GetMapping("")
    public DataResponseResult getUserPage(){
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setPageSize(10);
        sysUserDTO.setPageNumber(1);
        return DataResponseResult.success(fSysUserService.queryPage(sysUserDTO));
    }
}
