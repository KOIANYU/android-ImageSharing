package com.imagesharingproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageService {
    String uploadImage(MultipartFile... imageFile);
}
