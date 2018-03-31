package xyz.ndlr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.model.entity.Provider;
import xyz.ndlr.service.AuthHolder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController extends DefaultController {

    @Autowired
    ProviderController(AuthHolder authHolder) {
        super(authHolder);
    }

    @GetMapping(Route.ME)
    public ResponseEntity<List<Provider>> getMyProviders() {
        this.authHolder.getUser();
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getAllProviders() {
        return new ResponseEntity<>("a,b,c,d", HttpStatus.OK);
    }
}
