package com.imagesharingproject.pojo;

import lombok.Getter;

@Getter
public class FirstComment {
    private String content;
    private Long shareId;
    private Long userId;
    private String userName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", shareId=" + shareId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
