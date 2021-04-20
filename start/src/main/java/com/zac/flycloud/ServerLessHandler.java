package com.zac.flycloud;

import com.qcloud.scf.runtime.AbstractSpringHandler;

public class ServerLessHandler extends AbstractSpringHandler {

    @Override
    public void startApp() {
        // 修改为项目的入口主函数
        FlyCloudApplication.main(new String[]{""});
    }
}
