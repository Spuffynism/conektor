package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.facebook.shared.AttachmentType;

/**
 * A payload which has a multimedia AttachmentType.
 * {@link AttachmentType#isMultimedia()}
 */
public class MultimediaPayload extends AbstractPayload {
    @JsonProperty("url")
    private String url;
    @JsonProperty("is_reusable")
    private boolean reusable;

    public MultimediaPayload() {
    }

    public MultimediaPayload(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isReusable() {
        return reusable;
    }

    public void setReusable(boolean reusable) {
        this.reusable = reusable;
    }
}
