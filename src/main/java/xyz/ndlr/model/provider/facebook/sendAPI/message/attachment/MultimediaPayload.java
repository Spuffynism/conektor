package xyz.ndlr.model.provider.facebook.sendAPI.message.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * when AttachmentType is AUDIO, FILE, IMAGE or VIDEO payload
 */
public class MultimediaPayload extends AbstractPayload {
    // required
    @JsonProperty("url")
    private String url;
    // optionnal
    @JsonProperty("is_reusable")
    private boolean reusable;

    public MultimediaPayload(){}

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
