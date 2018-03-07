package xyz.ndlr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.service.AuthHolder;
import xyz.ndlr.service.UserService;

import java.util.List;


@RestController
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    private final UserService userService;
    private final AuthHolder authHolder;

    @Autowired
    public UserController(UserService userService, AuthHolder authHolder) {
        this.userService = userService;
        this.authHolder = authHolder;
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
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Exception> createUser(@RequestBody User user,
                                                UriComponentsBuilder ucBuilder) {
        try {
            userService.tryCreateNewUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        if (!authHolder.isMe(id) && !authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!userService.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
