package xyz.ndlr.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.ndlr.service.AuthHolder;

@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DefaultController {
    final AuthHolder authHolder;

    DefaultController(AuthHolder authHolder) {
        this.authHolder = authHolder;
    }
}
