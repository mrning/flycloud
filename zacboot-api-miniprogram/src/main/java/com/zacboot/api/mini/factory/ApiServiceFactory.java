package com.zacboot.api.mini.factory;

import com.zacboot.api.mini.service.BaseApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiServiceFactory {

    @Autowired
    private final Map<String, BaseApiService> serviceMap = new ConcurrentHashMap<>();

    public BaseApiService getService(String name){
        return serviceMap.get(name);
    }
}
