package com.springmvc.model.provider.facebook.sendAPI.message.attachment.template.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.template.TemplatePayload;
import com.springmvc.model.provider.facebook.sendAPI.message.attachment.template.TemplateType;
import com.springmvc.model.provider.facebook.sendAPI.message.button.AbstractButton;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public class ListTemplatePayload extends TemplatePayload {
    // optional
    @JsonProperty("sharable")
    private boolean sharable;
    // optional
    @JsonProperty("top_element_style")
    private TopElementStyle topElementStyle;
    // required
    @JsonProperty("elements")
    @Max(4)
    @Min(2)
    private List<Element> elements;
    // optional
    @JsonProperty("buttons")
    @Max(1)
    private List<AbstractButton> buttons;

    public ListTemplatePayload() {
        super(TemplateType.LIST);
    }
}
