package com.imagesharingproject.pojo;

import lombok.Getter;

@Getter
public class SecondComment {
    private String content;
    private Long parentCommentId; // 一级评论id
    private Long parentCommentUserId; // 一级评论的用户id
    private Long replyCommentId; // 被回复的评论id
    private Long replyCommentUserId; // 被回复的评论的用户id
    private Long shareId;
    private Long userId; // 评论人userId
    private String userName; // 评论人用户名

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public void setParentCommentUserId(Long parentCommentUserId) {
        this.parentCommentUserId = parentCommentUserId;
    }

    public void setReplyCommentId(Long replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public void setReplyCommentUserId(Long replyCommentUserId) {
        this.replyCommentUserId = replyCommentUserId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SecondComment{" +
                "content='" + content + '\'' +
                ", parentCommentId=" + parentCommentId +
                ", parentCommentUserId=" + parentCommentUserId +
                ", replyCommentId=" + replyCommentId +
                ", replyCommentUserId=" + replyCommentUserId +
                ", shareId=" + shareId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
