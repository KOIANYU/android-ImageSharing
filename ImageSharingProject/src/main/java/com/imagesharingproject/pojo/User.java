package com.imagesharingproject.pojo;

import lombok.Getter;

@Getter
public class User {
    private String avatar;
    private Long id;
    private String introduce;
    private Integer sex;
    private String username;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "avatar='" + avatar + '\'' +
                ", id=" + id +
                ", introduce='" + introduce + '\'' +
                ", sex=" + sex +
                ", username='" + username + '\'' +
                '}';
    }
}
