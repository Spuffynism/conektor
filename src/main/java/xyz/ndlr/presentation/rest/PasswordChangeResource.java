package xyz.ndlr.presentation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.domain.exception.password.PasswordException;
import xyz.ndlr.infrastructure.auth.AuthHolder;
import xyz.ndlr.security.auth.PasswordChange;
import xyz.ndlr.service.PasswordChangeService;

@RestController
@RequestMapping(name = "/password_change",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PasswordChangeResource {

    private final AuthHolder authHolder;
    private final PasswordChangeService passwordChangeService;

    @Autowired
    public PasswordChangeResource(AuthHolder authHolder,
                                  PasswordChangeService passwordChangeService) {
        this.authHolder = authHolder;
        this.passwordChangeService = passwordChangeService;
    }

    @PostMapping
    public ResponseEntity<Exception> changePassword(@RequestBody PasswordChange passwordChange)
            throws PasswordException {
        passwordChangeService.changePassword(authHolder.getUser(), passwordChange);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
