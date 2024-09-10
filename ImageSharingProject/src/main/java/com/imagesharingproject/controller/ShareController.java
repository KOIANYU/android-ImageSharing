package com.imagesharingproject.controller;

import com.imagesharingproject.pojo.Share;
import com.imagesharingproject.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share")
public class ShareController {

    private final ShareService shareService;

    @Autowired
    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @GetMapping
    public String getByUserId(@RequestParam Long userId) {
        System.out.println(userId);
        return shareService.getByUserId(userId);
    }

    @PostMapping("/add")
    public String add(@RequestBody Share share) {
        System.out.println(share);
        return shareService.add(share);
    }

    @PostMapping("/change")
    public String change(@RequestBody Share share) {
        System.out.println(share);
        return shareService.change(share);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long shareId, @RequestParam Long userId) {
        System.out.println(shareId);
        return shareService.delete(shareId, userId);
    }


    @GetMapping ("/detail")
    public String getDetail(@RequestParam Long shareId, @RequestParam Long userId) {
        System.out.println(shareId + " " + userId);
        return shareService.getDetail(shareId, userId);
    }

    @GetMapping("/myself")
    public String getMyself(@RequestParam Long userId) {
        System.out.println(userId);
        return shareService.getMyself(userId);
    }

    @GetMapping("/save")
    public String getSave(@RequestParam Long userId) {
        System.out.println(userId);
        return shareService.getSave(userId);
    }

    @PostMapping("/save")
    public String save(@RequestBody Share share) {
        System.out.println(share);
        return shareService.save(share);
    }

}
