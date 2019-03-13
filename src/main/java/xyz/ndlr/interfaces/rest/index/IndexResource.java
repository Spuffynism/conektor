package xyz.ndlr.interfaces.rest.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.domain.WelcomeMessage;
import xyz.ndlr.service.WelcomingService;

@RestController
@RequestMapping("/")
public class IndexResource {

    private final WelcomingService welcomingService;

    @Autowired
    public IndexResource(WelcomingService welcomingService) {
        this.welcomingService = welcomingService;
    }

    @GetMapping
    public ResponseEntity<WelcomeMessage> get() {
        WelcomeMessage welcome = welcomingService.produceWelcomeMessage();
        return new ResponseEntity<>(welcome, HttpStatus.OK);
    }
}
