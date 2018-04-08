package xyz.ndlr.model.provider.facebook.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public enum AttachmentType {
    @JsonProperty("audio")
    AUDIO,
    @JsonProperty("fallback")
    FALLBACK,
    @JsonProperty("file")
    FILE,
    @JsonProperty("image")
    IMAGE,
    @JsonProperty("location")
    LOCATION,
    @JsonProperty("template")
    TEMPLATE,
    @JsonProperty("video")
    VIDEO;

    public static final List<AttachmentType> MULTIMEDIA_TYPES =
            Arrays.asList(AUDIO, FILE, IMAGE, VIDEO);

    public boolean isMultimedia() {
        return MULTIMEDIA_TYPES.contains(this);
    }
}
