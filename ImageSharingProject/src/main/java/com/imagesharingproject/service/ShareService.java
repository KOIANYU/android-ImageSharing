package com.imagesharingproject.service;

import com.imagesharingproject.pojo.Share;

public interface ShareService {
    String getByUserId(Long userId);
    String add(Share share);
    String change(Share share);
    String delete(Long shareId, Long userId);
    String getDetail(Long shareId, Long userId);
    String getMyself(Long userId);
    String getSave(Long userId);
    String save(Share share);
}
