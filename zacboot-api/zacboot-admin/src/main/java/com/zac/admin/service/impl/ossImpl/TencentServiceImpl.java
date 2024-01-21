package com.zac.admin.service.impl.ossImpl;

import com.alibaba.fastjson2.JSONObject;
import com.zac.admin.service.UploadFileService;
import org.springframework.web.multipart.MultipartFile;

public class TencentServiceImpl implements UploadFileService {

    @Override
    public JSONObject upload(MultipartFile file, String fileDir, String customBucket) {
        return null;
    }

    @Override
    public Object initOSS(String endpoint, String accessKeyId, String accessKeySecret) {
        return null;
    }
}
