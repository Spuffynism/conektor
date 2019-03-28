package xyz.ndlr.presentation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.ndlr.domain.IAuthHolder;
import xyz.ndlr.domain.Limit;
import xyz.ndlr.domain.exception.EmailTakenException;
import xyz.ndlr.domain.exception.UnauthorizedException;
import xyz.ndlr.domain.exception.UserNotFoundException;
import xyz.ndlr.domain.exception.UsernameTakenException;
import xyz.ndlr.domain.password.exception.NonCompliantPasswordException;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserCreationRequest;
import xyz.ndlr.domain.user.UserId;
import xyz.ndlr.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = UserResource.PATH,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserResource {

    static final String PATH = "/users";
    private final IAuthHolder authHolder;
    private final UserService userService;

    @Autowired
    public UserResource(IAuthHolder authHolder, UserService userService) {
        this.authHolder = authHolder;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam Optional<Limit> limit)
            throws UnauthorizedException {
        List<User> users = userService.fetchAll(limit);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(Route.ID)
    public ResponseEntity<User> getUser(@PathVariable(Route.Attribute.ID) UserId id)
            throws UnauthorizedException, UserNotFoundException {
        User user = userService.fetchById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(Route.ME)
    public ResponseEntity<User> getMe() {
        return new ResponseEntity<>(authHolder.getUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserCreationRequest creationRequest,
                                     UriComponentsBuilder ucBuilder)
            throws UsernameTakenException, EmailTakenException, NonCompliantPasswordException {
        User user = userService.createNewUser(creationRequest);

        URI userLocation = ucBuilder.path(PATH + Route.ID).buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(userLocation).build();
    }

    @DeleteMapping(Route.ID)
    public ResponseEntity<User> deleteUser(@PathVariable(Route.Attribute.ID) UserId userId)
            throws UnauthorizedException, UserNotFoundException {
        userService.delete(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
