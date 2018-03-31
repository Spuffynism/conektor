package xyz.ndlr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.service.AuthHolder;
import xyz.ndlr.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends DefaultController {

    private final UserService userService;

    @Autowired
    public UserController(AuthHolder authHolder, UserService userService) {
        super(authHolder);
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        if (!authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        List<User> users = userService.getAll();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(Route.ID)
    public ResponseEntity<User> getUser(@PathVariable(Route.Attribute.ID) int id) {
        if (!authHolder.isMe(id) && !authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User user = userService.get(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
            userService.tryCreateNewUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(Route.ID)
    public ResponseEntity<User> updateUser(@PathVariable(Route.Attribute.ID) int id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping(Route.ID)
    public ResponseEntity<User> deleteUser(@PathVariable(Route.Attribute.ID) int id) {
        if (!authHolder.getUser().isAdmin())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!userService.exists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
