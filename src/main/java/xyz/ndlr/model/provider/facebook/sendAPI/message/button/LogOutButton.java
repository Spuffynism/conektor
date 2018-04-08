package xyz.ndlr.model.provider.facebook.sendAPI.message.button;

/**
 * https://developers.facebook.com/docs/messenger-platform/reference/buttons/logout
 */
public class LogOutButton extends AbstractButton {
    LogOutButton() {
        super(ButtonType.LOG_OUT);
    }
}
