package xyz.ndlr.presentation.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.ndlr.service.WelcomingService;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IndexResourceTest {

    @Mock
    WelcomingService welcomingService;

    @InjectMocks
    IndexResource indexResource;

    @Test
    public void whenGettingWelcomeMessage_producesWelcomeMessage() {
        indexResource.get();

        verify(welcomingService).produceWelcomeMessage();
    }
}