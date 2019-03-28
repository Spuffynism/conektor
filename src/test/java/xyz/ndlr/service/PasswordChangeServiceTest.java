package xyz.ndlr.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.ndlr.domain.IPasswordChangeRepository;
import xyz.ndlr.domain.password.IPasswordHasher;
import xyz.ndlr.domain.password.Password;
import xyz.ndlr.domain.password.PasswordChange;
import xyz.ndlr.domain.user.User;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeServiceTest {

    private static final User A_USER = new User();
    private static final PasswordChange A_PASSWORD_CHANGE =
            new PasswordChange(new Password("new password"), new Password("new password"),
                    new Password("current password"));

    @Mock
    IPasswordChangeRepository passwordChangeRepository;

    @Mock
    IPasswordHasher passwordHasher;

    @InjectMocks
    PasswordChangeService passwordChangeService;

    @Test
    public void given_whenChangingPassword_addsPasswordChangeAttempt()
            throws Exception {
        passwordChangeService.changeCurrentUserPassword(A_PASSWORD_CHANGE);

        verify(passwordChangeRepository)
                .addPasswordChangeAttempt(A_USER.getId());
    }
}