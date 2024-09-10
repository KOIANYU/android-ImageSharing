package com.imagesharingproject.service;

public interface LikeService {
    String getLike(Long userId);
    String addLike(Long shareId, Long userId);
    String cancelLike(Long likeId);
}
