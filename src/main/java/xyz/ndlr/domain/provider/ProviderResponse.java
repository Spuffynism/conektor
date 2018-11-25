package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.provider.facebook.sendAPI.message.Message;
import xyz.ndlr.domain.provider.facebook.sendAPI.message.TextMessage;

/**
 * All provider calls made return this
 */
public class ProviderResponse {
    private User user;
    private Message message;

    public ProviderResponse(User user, Message message) {
        this.user = user;
        this.message = message;
    }

    public ProviderResponse(User user, String message) {
        this(user, new TextMessage(message));
    }

    public static ProviderResponse notImplemented(User user) {
        return new ProviderResponse(user, "not implemented");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
