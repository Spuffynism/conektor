package xyz.ndlr.model.provider.facebook.sendAPI.message.quick_reply;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;

public class QuickReply {
    @JsonProperty("content_type")
    private QuickReplyContentType quickReplyContentType;
    /**
     * Required if content_type is 'text'. The text to display on the quick reply button. 20
     * character limit.
     */
    @JsonProperty("title")
    public String title;
    /**
     * Required if content_type is 'text'. Custom data that will be sent back to you via the
     * messaging_postbacks webhook event.
     * <p>
     * May be set to an empty string if image_url is set.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payload")
    @Max(1000)
    public String payload;
    /**
     * Optional. URL of image to display on the quick reply button for text quick replies. Image
     * should be a minimum of 24px x 24px. Larger images will be automatically cropped and resized.
     * <p>
     * Required if title is an empty string.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("image_url")
    public String imageUrl;

    public QuickReply(QuickReplyContentType quickReplyContentType, String title, String payload) {
        this.quickReplyContentType = quickReplyContentType;
        this.title = title;
        this.payload = payload;
    }
}
