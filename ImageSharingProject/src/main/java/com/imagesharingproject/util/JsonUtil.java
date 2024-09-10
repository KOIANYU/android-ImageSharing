package com.imagesharingproject.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().create();

    /**
     * 将 Map 转换成 JSON 字符串。
     * @param map 需要转换的 Map
     * @return JSON 字符串
     */
    public static String toJson(Map<String, Object> map) {
        return gson.toJson(map);
    }
}
