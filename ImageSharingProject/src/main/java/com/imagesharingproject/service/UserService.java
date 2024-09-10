package com.imagesharingproject.service;

import com.imagesharingproject.pojo.User;

public interface UserService {
    String login(String password, String username);
    String register(String password, String username);
    String getUserByName(String username);
    String updateUser(User user);
}
