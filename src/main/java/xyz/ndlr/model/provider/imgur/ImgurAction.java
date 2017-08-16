package xyz.ndlr.model.provider.imgur;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.model.provider.IProviderAction;

public enum ImgurAction implements IProviderAction {
    @JsonProperty("upload")
    UPLOAD,
    @JsonProperty("delete")
    DELETE;
}
