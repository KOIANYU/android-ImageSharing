package com.imagesharingproject.controller;

import com.imagesharingproject.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile... imageFile) {
        for (MultipartFile file : imageFile) {
            System.out.println(file.getOriginalFilename());
        }
        return imageService.uploadImage(imageFile);
    }

}
