package xyz.ndlr.model.provider.imgur;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import xyz.ndlr.model.provider.imgur.receive.Image;
import xyz.ndlr.model.provider.imgur.receive.UploadResponse;
import xyz.ndlr.model.provider.imgur.send.UploadPayload;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ImgurRepository {
    private static final String IMGUR_API_URI = "https://api.imgur.com/3/";
    private static String accessToken;
    private AsyncRestTemplate asyncRestTemplate;

    private static final MultiValueMap<String, String> headers;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("Authorization", "Bearer " + accessToken);

        headers = new LinkedMultiValueMap<>();
        headers.setAll(map);
    }

    public ImgurRepository() {
        asyncRestTemplate = new AsyncRestTemplate();
    }

    @Value("${imgur.access_token}")
    private void setAccessToken(String accessToken) {
        ImgurRepository.accessToken = accessToken;
    }

    ListenableFuture<Image> upload(UploadPayload payload) {
        HttpEntity<UploadPayload> uploadEntity = new HttpEntity<>(payload, headers);

        ListenableFuture<ResponseEntity<UploadResponse>> uploadResponse =
                asyncRestTemplate.postForEntity(IMGUR_API_URI + "image", uploadEntity,
                        UploadResponse.class);

        return new ImageAdapter(uploadResponse);
    }
}
