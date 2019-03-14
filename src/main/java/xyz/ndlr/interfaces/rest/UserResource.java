package xyz.ndlr.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.ndlr.domain.Limit;
import xyz.ndlr.domain.exception.UserNotFoundException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.service.AuthHolder;
import xyz.ndlr.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserResource {

    private final AuthHolder authHolder;
    private final UserService userService;

    @Autowired
    public UserResource(AuthHolder authHolder, UserService userService) {
        this.authHolder = authHolder;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam Limit limit) {
        if (!authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        List<User> users = userService.fetchAll(limit);
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(Route.ID)
    public ResponseEntity<User> getUser(@PathVariable(Route.Attribute.ID) UserId id) {
        if (!authHolder.isMe(id) && !authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User user;
        try {
            user = userService.fetchById(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(Route.ME)
    public ResponseEntity<User> getMe() {
        return new ResponseEntity<>(authHolder.getUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Exception> createUser(@RequestBody User user,
                                                UriComponentsBuilder ucBuilder) {
        try {
            userService.createNewUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(Route.ID)
    public ResponseEntity<User> deleteUser(@PathVariable(Route.Attribute.ID) UserId userId) {
        if (!authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            userService.delete(userId);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
