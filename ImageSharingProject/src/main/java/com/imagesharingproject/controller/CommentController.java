package com.imagesharingproject.controller;

import com.imagesharingproject.pojo.FirstComment;
import com.imagesharingproject.pojo.SecondComment;
import com.imagesharingproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/first")
    public String getFirstComment(@RequestParam Long shareId) {
        System.out.println(shareId);
        return commentService.getFirstComment(shareId);
    }

    @PostMapping("/first")
    public String addFirstComment(@RequestBody FirstComment firstComment) {
        System.out.println(firstComment);
        return commentService.addFirstComment(firstComment);
    }

    @GetMapping("/second")
    public String getSecondComment(@RequestParam Long commentId, @RequestParam Long shareId) {
        System.out.println(commentId + " " + shareId);
        return commentService.getSecondComment(commentId, shareId);
    }

    @PostMapping("/second")
    public String addSecondComment(@RequestBody SecondComment secondComment) {
        System.out.println(secondComment);
        return commentService.addSecondComment(secondComment);
    }
}
