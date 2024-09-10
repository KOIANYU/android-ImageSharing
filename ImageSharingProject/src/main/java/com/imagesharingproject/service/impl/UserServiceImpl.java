package com.imagesharingproject.service.impl;

import com.imagesharingproject.pojo.User;
import com.imagesharingproject.service.UserService;
import com.imagesharingproject.util.HeaderUtil;
import com.imagesharingproject.util.JsonUtil;
import com.imagesharingproject.util.api.UserApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    // 定义请求头参数
    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String login(String password, String username) {
        ResponseEntity<String> response = restTemplate.exchange( // ResponseEntity 包装返回结果
                UserApiUtil.UserLoginUrl(password, username),
                HttpMethod.POST,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String register(String password, String username) {
        Map<String, Object> bodyMap = new HashMap<>(); // 封装请求参数
        bodyMap.put("password", password);
        bodyMap.put("username", username);


        ResponseEntity<String> response = restTemplate.exchange(
                UserApiUtil.UserRegisterUrl(),
                HttpMethod.POST,
                new HttpEntity<>(JsonUtil.toJson(bodyMap), HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String getUserByName(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                UserApiUtil.UserGetUrl(username),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String updateUser(User user) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("avatar", user.getAvatar());
        bodyMap.put("id", user.getId());
        bodyMap.put("introduce", user.getIntroduce());
        bodyMap.put("sex", user.getSex());
        bodyMap.put("username", user.getUsername());

        ResponseEntity<String> response = restTemplate.exchange(
                UserApiUtil.UserUpdateUrl(),
                HttpMethod.POST,
                new HttpEntity<>(JsonUtil.toJson(bodyMap), HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }


}
