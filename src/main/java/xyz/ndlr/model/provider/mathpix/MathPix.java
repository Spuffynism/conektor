package xyz.ndlr.model.provider.mathpix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xyz.ndlr.model.provider.mathpix.request.Request;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MathPix {
    private static final String MATHPIX_API_URI = "https://api.mathpix.com/v3/latex";
    private static String appId;
    private static String appKey;
    private RestTemplate restTemplate;
    private static final MultiValueMap<String, String> headers;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        map.put("app_id", appId);
        map.put("app_key", appKey);

        headers = new LinkedMultiValueMap<>();
        headers.setAll(map);
    }

    public MathPix() {
        restTemplate = new RestTemplate();
    }

    @Value("${mathpix.app_id}")
    private void setAppId(String appId) {
        MathPix.appId = appId;
    }

    @Value("${mathpix.app_key}")
    private void setAppKey(String appKey) {
        MathPix.appKey = appKey;
    }

    public DetectionResult process(Request request) {
        HttpEntity<Request> uploadEntity = new HttpEntity<>(request, headers);

        ResponseEntity<DetectionResult> uploadResponse =
                restTemplate.postForEntity(MATHPIX_API_URI, uploadEntity, DetectionResult.class);

        return uploadResponse.getBody();
    }
}
