package xyz.ndlr.infrastructure.provider.help;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.ndlr.domain.parsing.ParsedMessage;
import xyz.ndlr.domain.provider.ProviderResponseFactory;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.provider.facebook.PipelinedMessage;
import xyz.ndlr.infrastructure.provider.facebook.webhook.Messaging;

import java.util.LinkedHashMap;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HelpServiceTest {
    private static final User AN_USER = new User();
    private static final Messaging A_MESSAGING = new Messaging();
    private static final ParsedMessage A_PARSED_MESSAGE = new ParsedMessage("",
            new LinkedHashMap<>());
    private static final PipelinedMessage A_PIPELINED_MESSAGE =
            new PipelinedMessage(A_MESSAGING, A_PARSED_MESSAGE);

    @Mock
    ProviderResponseFactory providerResponseFactory;

    @InjectMocks
    HelpService helpService;

    @Test
    public void whenApplying_respondsWithHelpMessage() {
        helpService.apply(AN_USER, A_PIPELINED_MESSAGE);
        verify(providerResponseFactory).createForUser(AN_USER, "");
    }
}