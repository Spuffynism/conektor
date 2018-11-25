package xyz.ndlr.domain.provider.facebook.sendAPI.message.quick_reply;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;

public class QuickReply {
    @JsonProperty("content_type")
    private QuickReplyContentType quickReplyContentType;
    /**
     * Required if content type is 'text'. The text to display on the quick reply button. 20
     * character limit.
     */
    @JsonProperty("title")
    private String title;
    /**
     * Required if content type is 'text'. Custom data that will be sent back to you via the
     * messaging_postbacks webhook event.
     * <p>
     * May be set to an empty string if image_url is set.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payload")
    @Max(1000)
    private String payload;
    /**
     * Optional. URL of image to display on the quick reply button for text quick replies. Image
     * should be a minimum of 24px x 24px. Larger images will be automatically cropped and resized.
     * <p>
     * Required if title is an empty string.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("image_url")
    private String imageUrl;

    public QuickReply(QuickReplyContentType quickReplyContentType, String title, String payload) {
        this.quickReplyContentType = quickReplyContentType;
        this.title = title;
        this.payload = payload;
    }

    public QuickReplyContentType getQuickReplyContentType() {
        return quickReplyContentType;
    }

    public void setQuickReplyContentType(QuickReplyContentType quickReplyContentType) {
        this.quickReplyContentType = quickReplyContentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
