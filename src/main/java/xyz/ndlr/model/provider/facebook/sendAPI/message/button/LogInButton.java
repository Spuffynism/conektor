package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * https://developers.facebook.com/docs/messenger-platform/reference/buttons/login
 */
public class LogInButton extends AbstractButton {

    @JsonProperty("url")
    private static String url;

    LogInButton() {
        super(ButtonType.LOG_IN);
    }

    @Value("${facebook.auth.log_in_url}")
    public void setUrl(String url) {
        LogInButton.url = url;
    }
}
