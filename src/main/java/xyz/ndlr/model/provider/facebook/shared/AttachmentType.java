package xyz.ndlr.model.provider.facebook.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

    private static final List<AttachmentType> MULTIMEDIA_TYPES = new ArrayList<>(
            Arrays.asList(AUDIO, FILE, IMAGE, VIDEO)
    );

    public static boolean isMultimedia(AttachmentType type) {
        return MULTIMEDIA_TYPES.contains(type);
    }
}
