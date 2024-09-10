package com.imagesharingproject.service;

public interface FocusService {
    String getFocus(Long userId);
    String addFocus(Long focusUserId, Long userId);
    String cancelFocus(Long focusUserId, Long userId);
}
