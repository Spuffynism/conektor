package xyz.ndlr.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class FakeController {
    private static final Logger logger = Logger.getLogger(FakeController.class);

    private final FacebookWebhookController facebookWebhookController;

    @Autowired
    public FakeController(FacebookWebhookController facebookWebhookController) {
        this.facebookWebhookController = facebookWebhookController;
    }

    @RequestMapping(value = "text", method = RequestMethod.GET)
    public ResponseEntity<?> sendText() {
        return null;
    }

    @RequestMapping(value = "image", method = RequestMethod.GET)
    public ResponseEntity<?> sendImage() {
        return null;
    }
}
