package com.imagesharingproject.controller;

import com.imagesharingproject.service.FocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/focus")
public class FocusController {

    private final FocusService focusService;

    @Autowired
    public FocusController(FocusService focusService) {
        this.focusService = focusService;
    }

    @GetMapping
    public String getFocus(@RequestParam Long userId) {
        System.out.println(userId);
        return focusService.getFocus(userId);
    }

    @PostMapping
    public String addFocus(@RequestParam Long focusUserId, @RequestParam Long userId) {
        return focusService.addFocus(focusUserId, userId);
    }

    @PostMapping("/cancel")
    public String cancelFocus(@RequestParam Long focusUserId, @RequestParam Long userId) {
        return focusService.cancelFocus(focusUserId, userId);
    }
}
