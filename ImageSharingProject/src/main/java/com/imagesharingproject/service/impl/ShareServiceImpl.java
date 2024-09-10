package com.imagesharingproject.service.impl;

import com.imagesharingproject.pojo.Share;
import com.imagesharingproject.service.ShareService;
import com.imagesharingproject.util.HeaderUtil;
import com.imagesharingproject.util.JsonUtil;
import com.imagesharingproject.util.api.ShareApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShareServiceImpl implements ShareService {

    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getByUserId(Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareGetAllUrl(userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String add(Share share) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareAddUrl(),
                HttpMethod.POST,
                new HttpEntity<>(getJsonData(share), HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String change(Share share) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareChangeUrl(),
                HttpMethod.POST,
                new HttpEntity<>(getJsonData(share), HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String delete(Long shareId, Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareDeleteUrl(shareId, userId),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String getDetail(Long shareId, Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareGetDetailUrl(shareId, userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String getMyself(Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareGetMyselfUrl(userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String getSave(Long userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareGetSaveUrl(userId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String save(Share share) {
        ResponseEntity<String> response = restTemplate.exchange(
                ShareApiUtil.ShareSaveUrl(),
                HttpMethod.POST,
                new HttpEntity<>(getJsonData(share), HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    public String getJsonData(Share share) {
        Map<String, Object> bodyMap = new HashMap<>();
        populateMap(bodyMap, share);
        return JsonUtil.toJson(bodyMap);
    }

    private void populateMap(Map<String, Object> bodyMap, Share share) {
        bodyMap.put("content", share.getContent());
        bodyMap.put("id", share.getId());
        bodyMap.put("imageCode", share.getImageCode());
        bodyMap.put("pUserId", share.getPUserId());
        bodyMap.put("title", share.getTitle());
    }


}
