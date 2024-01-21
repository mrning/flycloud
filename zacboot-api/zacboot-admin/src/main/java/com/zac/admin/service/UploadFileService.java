package com.zac.admin.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传抽象类
 */
public interface UploadFileService {

    JSONObject upload(MultipartFile file, String fileDir, String customBucket);

    Object initOSS(String endpoint, String accessKeyId, String accessKeySecret);

}
