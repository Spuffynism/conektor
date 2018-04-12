package xyz.ndlr.model.provider.mathpix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xyz.ndlr.model.provider.mathpix.request.Request;

import java.util.Collections;

@Repository
public class MathPix {
    private static final String MATHPIX_API_URI = "https://api.mathpix.com/v3/latex/";
    private static final String APP_ID_HEADER = "app_id";
    private static final String APP_KEY_HEADER = "app_key";

    private RestTemplate restTemplate;
    private static final MultiValueMap<String, String> headers;

    static {
        headers = new LinkedMultiValueMap<>();
        headers.put("Content-Type", Collections.singletonList("application/json"));
    }

    public MathPix() {
        restTemplate = new RestTemplate();
    }

    @Value("${mathpix.app_id}")
    private void setAppId(String appId) {
        headers.put(APP_ID_HEADER, Collections.singletonList(appId));
    }

    @Value("${mathpix.app_key}")
    private void setAppKey(String appKey) {
        headers.put(APP_KEY_HEADER, Collections.singletonList(appKey));
    }

    DetectionResult process(Request request) {
        HttpEntity<Request> uploadEntity = new HttpEntity<>(request, headers);

        ResponseEntity<DetectionResult> uploadResponse =
                restTemplate.postForEntity(MATHPIX_API_URI, uploadEntity, DetectionResult.class);

        return uploadResponse.getBody();
    }
}
