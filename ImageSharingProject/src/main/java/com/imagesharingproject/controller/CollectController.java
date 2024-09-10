package com.imagesharingproject.controller;

import com.imagesharingproject.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
public class CollectController {

    private final CollectService collectService;

    @Autowired
    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @GetMapping
    public String getByUserId(@RequestParam Long userId) {
        System.out.println(userId);
        return collectService.getByUserId(userId);
    }

    @PostMapping
    public String addCollect(@RequestParam Long shareId, @RequestParam Long userId) {
        System.out.println(shareId + " " + userId);
        return collectService.addCollect(shareId, userId);
    }

    @PostMapping("/cancel")
    public String cancelCollect(@RequestParam Long collectId) {
        System.out.println(collectId);
        return collectService.cancelCollect(collectId);
    }

}
