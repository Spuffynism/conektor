package xyz.ndlr.interfaces.rest.index;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexResource {

    @GetMapping
    public ResponseEntity<WelcomeMessage> get() {
        WelcomeMessage welcome = new WelcomeMessage("this is conektor");
        return new ResponseEntity<>(welcome, HttpStatus.OK);
    }
}
