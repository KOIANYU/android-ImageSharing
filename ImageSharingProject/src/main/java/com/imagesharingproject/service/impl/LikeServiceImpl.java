package com.imagesharingproject.service.impl;

import com.imagesharingproject.service.LikeService;
import com.imagesharingproject.util.HeaderUtil;
import com.imagesharingproject.util.api.LikeApiUtil;
import com.imagesharingproject.util.api.UserApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LikeServiceImpl implements LikeService {

    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getLike(Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                LikeApiUtil.LikeGetUrl(userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String addLike(Long shareId, Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                LikeApiUtil.LikeAddUrl(shareId, userId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String cancelLike(Long likeId) {
        ResponseEntity<String> response = restTemplate.exchange(
                LikeApiUtil.LikeCancelUrl(likeId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }
}
