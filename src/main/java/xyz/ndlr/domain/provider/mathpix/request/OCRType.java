package xyz.ndlr.domain.provider.mathpix.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OCRType {
    @JsonProperty("math")
    MATH,
    @JsonProperty("text")
    TEXT;

    public static final OCRType DEFAULT = MATH;
}
