package xyz.ndlr.domain;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.ndlr.domain.dispatching.ProviderResponseToSendablePayloadAdapter;
import xyz.ndlr.domain.exception.CannotSendMessageException;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.facebook.FacebookMessageSender;
import xyz.ndlr.domain.provider.facebook.sendAPI.SendablePayload;

/**
 * Consumes & sends messages produced.
 */
@Component
public class FacebookMessageConsumer implements Runnable {
    private static final Logger logger = Logger.getLogger(FacebookMessageConsumer.class);

    private final ProviderResponseQueue sharedResponseQueue;
    private final FacebookMessageSender messageSender;
    private final ProviderResponseToSendablePayloadAdapter adapter;

    private volatile boolean consume;

    public FacebookMessageConsumer(ProviderResponseQueue sharedResponseQueue,
                                   FacebookMessageSender messageSender,
                                   ProviderResponseToSendablePayloadAdapter adapter) {
        this.messageSender = messageSender;
        this.sharedResponseQueue = sharedResponseQueue;
        this.adapter = adapter;
    }

    public void startConsuming() {
        consume = true;

        new Thread(this).start();
    }

    public void stopConsuming() {
        consume = false;
    }

    @Override
    public void run() {
        while (consume) {
            try {
                ProviderResponse providerResponse = sharedResponseQueue.take();
                SendablePayload sendablePayload = adapter.apply(providerResponse);

                messageSender.send(sendablePayload);
            } catch (CannotSendMessageException e) {
                logger.error(e);
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
