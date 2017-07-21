package com.springmvc.model.provider.imgur;

import com.springmvc.model.provider.imgur.receive.UploadResponse;
import com.springmvc.model.provider.imgur.send.UploadPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ImgurRepository {
    private static final String IMGUR_API_URI = "https://api.imgur.com/3/";
    private static String accessToken;
    private RestTemplate restTemplate;

    public ImgurRepository() {
        restTemplate = new RestTemplate();
    }

    @Value("${imgur.access_token}")
    private void setAccessToken(String token) {
        accessToken = token;
    }

    public UploadResponse upload(UploadPayload payload) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", "Bearer " + accessToken);

        headers.setAll(map);

        HttpEntity<UploadPayload> uploadEntity = new HttpEntity<>(payload, headers);

        ResponseEntity<UploadResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(IMGUR_API_URI + "image",
                    uploadEntity, UploadResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UploadResponse uploadResponse = null;
        try {
            if (responseEntity == null)
                throw new NullPointerException();
            uploadResponse = responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uploadResponse;
    }
}
