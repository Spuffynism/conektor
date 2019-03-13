package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.WelcomeMessage;

@Service
public class WelcomingService {
    public WelcomeMessage produceWelcomeMessage() {
        return new WelcomeMessage("this is conektor");
    }
}
