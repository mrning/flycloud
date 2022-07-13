package com.zacboot.common.base.utils;

import java.util.*;

public class ConverUtil {

    /**
     * 将map的key全部转成小写
     * @param list
     * @return
     */
    public static List<Map<String, Object>> toLowerCasePageList(List<Map<String, Object>> list){
        List<Map<String, Object>> select = new ArrayList<>();
        for (Map<String, Object> row : list) {
            Map<String, Object> resultMap = new HashMap<>();
            Set<String> keySet = row.keySet();
            for (String key : keySet) {
                String newKey = key.toLowerCase();
                resultMap.put(newKey, row.get(key));
            }
            select.add(resultMap);
        }
        return select;
    }
}
