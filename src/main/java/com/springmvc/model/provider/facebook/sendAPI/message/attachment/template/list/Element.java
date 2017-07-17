package com.springmvc.model.provider.facebook.sendAPI.message.attachment.template.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.provider.facebook.sendAPI.message.button.AbstractButton;
import com.springmvc.model.provider.facebook.sendAPI.message.button.URLButton;

import javax.validation.constraints.Max;
import java.util.List;

class Element {
    // required
    @JsonProperty("title")
    @Max(80)
    private String title;

    // optional
    @JsonProperty("subtitle")
    @Max(80)
    private String subtitle;

    // optional - required if the parent payload's TopElementStyle is LARGE
    @JsonProperty("image_url")
    private String imageURL;

    /**
     * optional - The default_action behaves like a URL Button and contains the same fields
     * except that the title field is not needed
     */
    @JsonProperty("default_action")
    private URLButton defaultAction;

    // optional
    @JsonProperty("buttons")
    private List<AbstractButton> buttons;
}
