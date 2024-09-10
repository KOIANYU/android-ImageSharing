package com.imagesharingproject.service.impl;

import com.imagesharingproject.pojo.FirstComment;
import com.imagesharingproject.pojo.SecondComment;
import com.imagesharingproject.service.CommentService;
import com.imagesharingproject.util.HeaderUtil;
import com.imagesharingproject.util.api.CommentApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public String getFirstComment(Long shareId) {
        ResponseEntity<String> response = restTemplate.exchange(
                CommentApiUtil.CommentGetFirstUrl(shareId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String addFirstComment(FirstComment firstComment) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("content", firstComment.getContent());
        bodyMap.put("shareId", firstComment.getShareId());
        bodyMap.put("userId", firstComment.getUserId());
        bodyMap.put("userName", firstComment.getUserName());

        ResponseEntity<String> response = restTemplate.exchange(
                CommentApiUtil.CommentAddFirstUrl(),
                HttpMethod.POST,
                new HttpEntity<>(bodyMap, HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String getSecondComment(Long commentId, Long shareId) {
        ResponseEntity<String> response = restTemplate.exchange(
                CommentApiUtil.CommentGetSecondUrl(commentId, shareId),
                HttpMethod.GET,
                new HttpEntity<>(HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );
        return response.getBody();
    }

    @Override
    public String addSecondComment(SecondComment secondComment) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("content", secondComment.getContent());
        bodyMap.put("parentCommentId", secondComment.getParentCommentId());
        bodyMap.put("parentCommentUserId", secondComment.getParentCommentUserId());
        bodyMap.put("replyCommentId", secondComment.getReplyCommentId());
        bodyMap.put("replyCommentUserId", secondComment.getReplyCommentUserId());
        bodyMap.put("shareId", secondComment.getShareId());
        bodyMap.put("userId", secondComment.getUserId());
        bodyMap.put("userName", secondComment.getUserName());

        ResponseEntity<String> response  = restTemplate.exchange(
                CommentApiUtil.CommentAddSecondUrl(),
                HttpMethod.POST,
                new HttpEntity<>(bodyMap, HeaderUtil.setHeader(appId, appSecret)),
                String.class
        );

        return response.getBody();
    }
}
