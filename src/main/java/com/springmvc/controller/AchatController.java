package com.springmvc.controller;

import com.springmvc.model.achat.Achat;
import com.springmvc.model.achat.AchatService;
import com.springmvc.model.jambon.JambonService;
import com.springmvc.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/achats/", "/achats"})
public class AchatController {

    private final AchatService achatService;
    private final JambonService jambonService;
    private final UserService userService;

    @Autowired
    public AchatController(AchatService achatService, JambonService jambonService,
                           UserService userService) {
        this.achatService = achatService;
        this.jambonService = jambonService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Achat>> listAllAchats() {
        List<Achat> achats = achatService.getAll();
        if (achats.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(achats, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Achat> createAchat(@RequestBody Achat achat) {
        if (achat.getJambon() == null || !jambonService.existe(achat.getJambon().getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        achat = achatService.acheter(achat);

        return new ResponseEntity<>(achat, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Achat> getAchat(@PathVariable("id") int id) {
        Achat achat = achatService.get(id);

        if(achat != null && achat.getUser().getId() != userService.getAuthenticatedUser().getId()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (achat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(achat, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Achat>> getUserAchats(@PathVariable("id") int userId) {
        if(userId != userService.getAuthenticatedUser().getId()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Achat> achats = achatService.getByUser(userId);
        if (achats == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(achats, HttpStatus.OK);
    }

}
