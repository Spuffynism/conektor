package xyz.ndlr.domain;

import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;

public interface IAuthHolder {
    boolean isAuthenticated();

    User getUser();

    boolean isMe(UserId userId);

    boolean isMe(User user);
}
