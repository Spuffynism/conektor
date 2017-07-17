package com.springmvc.model.provider.facebook.sendAPI.message.attachment.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.AbstractPayload;

public class TemplatePayload extends AbstractPayload {
    @JsonProperty("template_type")
    private TemplateType type;

    public TemplatePayload(){}

    public TemplatePayload(TemplateType type) {
        this.type = type;
    }
}
