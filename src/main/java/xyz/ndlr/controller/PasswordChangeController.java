package xyz.ndlr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.exception.InvalidPasswordException;
import xyz.ndlr.security.auth.PasswordChange;
import xyz.ndlr.service.AuthHolder;
import xyz.ndlr.service.PasswordChangeService;

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
    public ResponseEntity<Exception> changePassword(@RequestBody PasswordChange passwordChange) {
        try {
            passwordChangeService.tryChangePassword(authHolder.getUser(), passwordChange);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
