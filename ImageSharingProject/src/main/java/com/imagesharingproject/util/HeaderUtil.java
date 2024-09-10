package com.imagesharingproject.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeaderUtil {

    /**
     * 设置请求头信息
     * @return 返回请求头信息
     */
    public static HttpHeaders setHeader(String appId, String appSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("appId", appId);
        headers.add("appSecret", appSecret);
        return headers;
    }

}
