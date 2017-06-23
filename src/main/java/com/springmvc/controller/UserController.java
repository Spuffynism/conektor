package com.springmvc.controller;

import com.springmvc.model.User;
import com.springmvc.service.AuthHolder;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        if(authHolder.getUser().isAccountNonLocked())
            return new ResponseEntity<>(HttpStatus.OK);

        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        User user = userService.get(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<User> getMe() {
        return new ResponseEntity<>(userService.getAuthenticatedUser(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (userService.existe(user.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        //TODO Valider données reçues ?
        userService.ajouter(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        User currentUser = userService.get(id);
        if (!userService.existe(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.modifier(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        if (!userService.existe(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.supprimer(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
