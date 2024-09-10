package com.imagesharingproject.controller;

import com.imagesharingproject.pojo.User;
import com.imagesharingproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String password, @RequestParam String username) {
        System.out.println(password + " " + username);
        return userService.login(password, username);
    }

    @PostMapping("/register")
    public String register(@RequestParam String password, @RequestParam String username) {
        System.out.println(password + " " + username);
        return userService.register(password, username);
    }

    @GetMapping("/getUserByName")
    public String getUserByName(@RequestParam String username){
        System.out.println(username);
        return userService.getUserByName(username);
    }

    @PostMapping("/update")
    public String updateUser(@RequestBody User user){
        System.out.println(user);
        return userService.updateUser(user);
    }

}
