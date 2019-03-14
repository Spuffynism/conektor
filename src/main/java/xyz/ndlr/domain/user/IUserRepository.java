package xyz.ndlr.domain.user;

import xyz.ndlr.domain.Email;

import java.util.List;

public interface IUserRepository {

    User get(Username username);

    User get(Email email);

    User get(UserId userId);

    List<User> getAll();

    User add(User user);

    void delete(UserId userId);

    Boolean exists(UserId userId);
}
