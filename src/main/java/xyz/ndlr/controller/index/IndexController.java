package xyz.ndlr.controller.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.ndlr.model.dispatching.mapping.ProviderActionRepository;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

@RestController
@RequestMapping("/")
public class IndexController {
    private ProviderActionRepository providerActionRepository;

    @Autowired
    IndexController(ProviderActionRepository providerActionRepository) {
        this.providerActionRepository = providerActionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<WelcomeMessage> get() {
        WelcomeMessage welcome = new WelcomeMessage("this is conektor");
        BiFunction<User, PipelinedMessage, ProviderResponse> func = providerActionRepository
                .getAction("trello", "test");
        func.apply(null, null);
        return new ResponseEntity<>(welcome, HttpStatus.OK);
    }
}
