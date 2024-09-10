package com.imagesharingproject.pojo;

import lombok.Getter;

@Getter
public class Share {
    private String content;
    private Long id;
    private Long imageCode;
    private Long pUserId;
    private String title;

    @Override
    public String toString() {
        return "Share{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", imageCode=" + imageCode +
                ", pUserId=" + pUserId +
                ", title='" + title + '\'' +
                '}';
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImageCode(Long imageCode) {
        this.imageCode = imageCode;
    }

    public void setpUserId(Long pUserId) {
        this.pUserId = pUserId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
