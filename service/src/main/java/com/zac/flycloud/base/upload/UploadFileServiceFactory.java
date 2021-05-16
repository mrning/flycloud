package com.zac.flycloud.base.upload;

import com.zac.flycloud.base.upload.impl.AliOssServiceImpl;
import com.zac.flycloud.base.upload.impl.TencentServiceImpl;
import com.zac.flycloud.enums.UploadClientEnum;

public class UploadFileServiceFactory {

    public static UploadFileService getUploadService(String uploadClient){

        UploadClientEnum clientEnum = UploadClientEnum.valueOf(uploadClient);
        switch (clientEnum){
            case OSS_ALI_CLIENT:
                return new AliOssServiceImpl();
            case OSS_TENCENT_CLIENT:
                return new TencentServiceImpl();
        }
        return new AliOssServiceImpl();
    }
}
