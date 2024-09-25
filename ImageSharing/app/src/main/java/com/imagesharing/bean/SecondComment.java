package com.imagesharing.bean;

public class SecondComment {
    private String userName;
    private String content;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SecondComment{" +
                "userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
