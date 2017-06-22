package com.springmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
