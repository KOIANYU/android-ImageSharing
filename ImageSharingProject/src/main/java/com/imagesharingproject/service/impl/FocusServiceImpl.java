package com.imagesharingproject.service.impl;

import com.imagesharingproject.service.FocusService;
import com.imagesharingproject.util.HeaderUtil;
import com.imagesharingproject.util.api.FocusApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FocusServiceImpl implements FocusService {

    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getFocus(Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                FocusApiUtil.FocusGetUrl(userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String addFocus(Long focusUserId, Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                FocusApiUtil.FocusAddUrl(focusUserId, userId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String cancelFocus(Long focusUserId, Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                FocusApiUtil.FocusCancelUrl(focusUserId, userId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }
}
