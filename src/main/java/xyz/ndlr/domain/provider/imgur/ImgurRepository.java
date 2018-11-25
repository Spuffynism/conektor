package xyz.ndlr.domain.provider.imgur;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import xyz.ndlr.domain.provider.imgur.receive.Image;
import xyz.ndlr.domain.provider.imgur.receive.UploadResponse;
import xyz.ndlr.domain.provider.imgur.send.UploadPayload;

import java.util.Collections;

@Repository
public class ImgurRepository {
    private static final String IMGUR_API_URI = "https://api.imgur.com/3/";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private AsyncRestTemplate asyncRestTemplate;

    private static final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    static {
        headers.put("Content-Type", Collections.singletonList("application/json"));
    }

    public ImgurRepository() {
        asyncRestTemplate = new AsyncRestTemplate();
    }

    @Value("${imgur.access_token}")
    private void setAccessToken(String accessToken) {
        headers.put(AUTHORIZATION_HEADER, Collections.singletonList("Bearer " + accessToken));
    }

    ListenableFuture<Image> upload(UploadPayload payload) {
        HttpEntity<UploadPayload> uploadEntity = new HttpEntity<>(payload, headers);

        ListenableFuture<ResponseEntity<UploadResponse>> uploadResponse =
                asyncRestTemplate.postForEntity(IMGUR_API_URI + "image", uploadEntity,
                        UploadResponse.class);

        return new ImageAdapter(uploadResponse);
    }
}
