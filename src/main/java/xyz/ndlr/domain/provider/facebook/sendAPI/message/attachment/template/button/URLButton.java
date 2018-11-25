package xyz.ndlr.domain.provider.facebook.sendAPI.message.attachment.template.button;

import com.fasterxml.jackson.annotation.JsonProperty;

public class URLButton extends AbstractButton {
    // required
    @JsonProperty("url")
    private String url;
    // required
    @JsonProperty("title")
    private String title;
    // optional
    @JsonProperty("webview_height_ratio")
    private WebviewHeightRatio webviewHeightRatio;
    // optional
    @JsonProperty("messenger_extensions")
    private boolean messengerExtensions;
    // optional
    @JsonProperty("fallback_url")
    private String fallbackUrl;
    // optional
    @JsonProperty("webview_share_button")
    private String webviewShareButton;

    public URLButton() {
        super(ButtonType.URL);
    }
}
