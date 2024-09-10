package com.imagesharingproject.controller;

import com.imagesharingproject.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public String getLike(@RequestParam Long userId) {
        System.out.println(userId);
        return likeService.getLike(userId);
    }

    @PostMapping
    public String addLike(@RequestParam Long shareId, @RequestParam Long userId) {
        System.out.println(shareId + " " + userId);
        return likeService.addLike(shareId, userId);
    }

    @PostMapping("/cancel")
    public String cancelLike(@RequestParam Long likeId) {
        System.out.println(likeId);
        return likeService.cancelLike(likeId);
    }

}
