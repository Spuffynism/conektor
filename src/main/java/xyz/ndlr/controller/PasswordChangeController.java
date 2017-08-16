package xyz.ndlr.controller;

import xyz.ndlr.exception.InvalidPasswordException;
import xyz.ndlr.security.auth.NewPassword;
import xyz.ndlr.service.AuthHolder;
import xyz.ndlr.service.PasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/password_change")
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;
    private final AuthHolder authHolder;

    @Autowired
    public PasswordChangeController(PasswordChangeService passwordChangeService,
                                    AuthHolder authHolder) {
        this.passwordChangeService = passwordChangeService;
        this.authHolder = authHolder;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Exception> changePassword(@RequestBody NewPassword newPassword) {
        try {
            passwordChangeService.tryChangePassword(authHolder.getUser(), newPassword);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
