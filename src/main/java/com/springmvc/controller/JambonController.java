package com.springmvc.controller;

import com.springmvc.model.jambon.Jambon;
import com.springmvc.model.jambon.JambonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/jambons/", "/jambons"})
public class JambonController {

    private final JambonService jambonService;

    @Autowired
    public JambonController(JambonService jambonService) {
        this.jambonService = jambonService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Jambon>> listAllJambons() {
        List<Jambon> jambons = jambonService.getAll();
        if (jambons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jambons, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Jambon> getJambon(@PathVariable("id") int id) {
        Jambon jambon = jambonService.get(id);
        if (jambon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jambon, HttpStatus.OK);
    }

}
