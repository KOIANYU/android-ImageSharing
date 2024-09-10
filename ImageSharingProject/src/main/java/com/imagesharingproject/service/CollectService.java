package com.imagesharingproject.service;

public interface CollectService {
    String getByUserId(Long userId);
    String addCollect(Long shareId, Long userId);
    String cancelCollect(Long collectId);
}
