package xyz.ndlr.presentation.rest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.ndlr.domain.password.Password;
import xyz.ndlr.domain.password.PasswordChange;
import xyz.ndlr.domain.password.exception.NonMatchingCurrentPasswordException;
import xyz.ndlr.domain.password.exception.PasswordException;
import xyz.ndlr.service.PasswordChangeService;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeResourceTest {

    private static final PasswordChange A_PASSWORD_CHANGE =
            new PasswordChange(new Password("new password"), new Password("new password"),
                    new Password("current password"));

    @Mock
    PasswordChangeService passwordChangeService;

    @InjectMocks
    PasswordChangeResource passwordChangeResource;

    @Test
    public void whenChangingPassword_changesCurrentUserPassword() throws PasswordException {
        passwordChangeResource.changePassword(A_PASSWORD_CHANGE);
    }

    @Test(expected = NonMatchingCurrentPasswordException.class)
    public void whenPasswordIsInvalid_throwsPasswordException() throws PasswordException {
        doThrow(NonMatchingCurrentPasswordException.class)
                .when(passwordChangeService)
                .changeCurrentUserPassword(A_PASSWORD_CHANGE);

        passwordChangeResource.changePassword(A_PASSWORD_CHANGE);
    }

    @Test
    public void whenChangingPassword_producesOKResponse() throws PasswordException {
        ResponseEntity response = passwordChangeResource.changePassword(A_PASSWORD_CHANGE);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}