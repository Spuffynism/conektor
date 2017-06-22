package com.springmvc.controller;

import com.springmvc.model.user.UserService;
import com.springmvc.security.auth.NewPassword;
import com.springmvc.security.auth.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/change_password/", "/change_password"})
public class ChangePasswordController {
    private final UserService userService;

    @Autowired
    public ChangePasswordController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Exception> changePassword(@RequestBody NewPassword newPassword) {
        int userId = userService.getAuthenticatedUser().getId();

        try {
            userService.ajouterTentativeChangementMotDePasse(userId);

            userService.changerMotDePasse(newPassword);

            userService.resetTentativesChangementMotDePasse(userId);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
