package com.imagesharingproject.service.impl;

import com.imagesharingproject.service.ImageService;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${Header.appId}")
    private String appId;
    @Value("${Header.appSecret}")
    private String appSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String uploadImage(MultipartFile... imageFile) {
        String url = "https://api-store.openguet.cn/api/member/photo/image/upload";

        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Accept", "application/json, text/plain, */*");
        httpPost.setHeader("appId", appId);
        httpPost.setHeader("appSecret", appSecret);
        httpPost.setHeader("Content-Type", "multipart/form-data");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (MultipartFile file : imageFile) {
            if (file != null && !file.isEmpty()) {
                try {
                    builder.addBinaryBody("file", file.getInputStream(), ContentType.APPLICATION_OCTET_STREAM, file.getOriginalFilename());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        String resp = null;
        CloseableHttpResponse response = null;
        try {
            response = (CloseableHttpResponse) httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Handle the exception as needed
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resp;
    }

}
