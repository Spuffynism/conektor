package com.springmvc.controller;

import com.springmvc.model.User;
import com.springmvc.security.auth.NewPassword;
import com.springmvc.security.auth.exception.InvalidPasswordException;
import com.springmvc.service.AuthHolder;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthHolder authHolder;

    @Autowired
    public UserController(UserService userService, AuthHolder authHolder) {
        this.userService = userService;
        this.authHolder = authHolder;
    }

    @RequestMapping(value="/me/date_created", method = RequestMethod.GET)
    public ResponseEntity<Date> getDateModified() {
        Date dateModified = userService.getDateCreated(authHolder.getUser().getId());

        return new ResponseEntity<>(dateModified, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        if (!authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        List<User> users = userService.getAll();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") int id,
                                        @RequestParam("accounts") boolean includeAccounts) {
        if (!authHolder.isMe(id) && !authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User user = userService.get(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<User> getMe() {
        return new ResponseEntity<>(authHolder.getUser(), HttpStatus.OK);
    }

    /**
     * User account creation.
     *
     * @param user
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (userService.exists(user.getId()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        //TODO Filter user properties
        userService.add(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        if (!authHolder.isMe(id) && !authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User currentUser = userService.get(id);
        if (!userService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //TODO Filter username & email and then only update those properties
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.update(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        if (!authHolder.isMe(id) && !authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!userService.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.delete(id);

        //TODO Return OK?
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public ResponseEntity<Exception> changePassword(@RequestBody NewPassword newPassword) {
        int userId = authHolder.getUser().getId();

        try {
            tryChangePassword(userId, newPassword);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void tryChangePassword(int userId, NewPassword newPassword)
            throws InvalidPasswordException {
        userService.addAttemptedPasswordChanges(userId);
        userService.changePassword(authHolder.getUser(), newPassword);
        userService.resetAttemptedPasswordChanges(userId);
    }
}
