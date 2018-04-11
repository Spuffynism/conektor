package xyz.ndlr.model.provider.mathpix.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.mathpix.Region;

import java.util.Base64;
import java.util.List;

public class Request {
    private static final String BASE64_DATA_PREFIX = "data:image/jpeg;base64,";

    @JsonProperty("region")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Region region;
    /**
     * [“math”?, “text”?]
     */
    @JsonProperty("ocr")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OCRType> ocrTypes;
    /**
     * number in [0,1]
     */
    @JsonProperty("confidence_threshold")
    private double confidenceThreshold;
    /**
     * number in [0,1]
     */
    @JsonProperty("confidence_rate_threshold")
    private double confidenceRateThreshold;

    @JsonProperty("src")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String source;
    /**
     * Accepted as an alternative to {@link Request#source}.
     */
    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    public Request(String url) {
        this.url = url;
    }

    public Request(byte[] fileBytes) {
        this.setSource(fileBytes);
    }

    public String getSource() {
        return source;
    }

    public void setSource(byte[] fileBytes) {
        this.source = BASE64_DATA_PREFIX + new String(Base64.getEncoder().encode(fileBytes));
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
