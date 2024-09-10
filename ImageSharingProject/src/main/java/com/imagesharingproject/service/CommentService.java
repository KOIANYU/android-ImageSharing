package com.imagesharingproject.service;

import com.imagesharingproject.pojo.FirstComment;
import com.imagesharingproject.pojo.SecondComment;

public interface CommentService {
    String getFirstComment(Long shareId);
    String addFirstComment(FirstComment firstComment);
    String getSecondComment(Long commentId, Long shareId);
    String addSecondComment(SecondComment secondComment);
}
