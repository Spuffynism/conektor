package xyz.ndlr.infrastructure.provider.facebook.sendAPI.message.attachment.template.button;

/**
 * https://developers.facebook.com/docs/messenger-platform/reference/buttons/logout
 */
public class LogOutButton extends AbstractButton {
    LogOutButton() {
        super(ButtonType.LOG_OUT);
    }
}
