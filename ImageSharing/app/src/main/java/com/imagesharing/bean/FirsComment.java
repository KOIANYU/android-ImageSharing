package com.imagesharing.bean;

import java.util.List;

public class FirsComment {
    private String userName;
    private String content;
    private String createTime;
    private List<SecondComment> secondComments;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<SecondComment> getSecondComments() {
        return secondComments;
    }

    public void setSecondComments(List<SecondComment> secondComments) {
        this.secondComments = secondComments;
    }

    @Override
    public String toString() {
        return "FirsComment{" +
                "userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", secondComments=" + secondComments +
                '}';
    }
}
