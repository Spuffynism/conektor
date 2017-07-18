package com.springmvc.model.provider.imgur;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.IProviderAction;

public enum ImgurAction implements IProviderAction {
    @JsonProperty("upload")
    UPLOAD,
    @JsonProperty("delete")
    DELETE;
}
