package com.imagesharingproject.service.impl;

import com.imagesharingproject.service.CollectService;
import com.imagesharingproject.util.HeaderUtil;
import com.imagesharingproject.util.api.CollectApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CollectServiceImpl implements CollectService {

    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getByUserId(Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                CollectApiUtil.CollectUserUrl(userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String addCollect(Long shareId, Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                CollectApiUtil.CollectShareUrl(shareId, userId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String cancelCollect(Long collectId) {
        ResponseEntity<String> response = restTemplate.exchange(
                CollectApiUtil.CollectCancelUrl(collectId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

}
