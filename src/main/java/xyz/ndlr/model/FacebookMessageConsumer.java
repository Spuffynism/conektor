package xyz.ndlr.model;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.ndlr.exception.CannotSendMessageException;
import xyz.ndlr.model.dispatching.ProviderResponseToSendPayloadAdapter;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.FacebookMessageSender;
import xyz.ndlr.model.provider.facebook.sendAPI.SendablePayload;

/**
 * Consumes & sends messages produced.
 */
@Component
public class FacebookMessageConsumer implements Runnable {
    private static final Logger logger = Logger.getLogger(FacebookMessageConsumer.class);

    private final ProviderResponseQueue sharedResponseQueue;
    private final FacebookMessageSender messageSender;
    private final ProviderResponseToSendPayloadAdapter adapter;

    private volatile boolean consume;

    public FacebookMessageConsumer(ProviderResponseQueue sharedResponseQueue,
                                   FacebookMessageSender messageSender,
                                   ProviderResponseToSendPayloadAdapter adapter) {
        this.messageSender = messageSender;
        this.sharedResponseQueue = sharedResponseQueue;
        this.adapter = adapter;
    }

    public void startConsuming() {
        consume = true;
    }

    public void stopConsuming() {
        consume = false;
    }

    @Override
    public void run() {
        while (consume) {
            try {
                // Maybe find out here if ProviderResponse will become a MessagePayload or
                // SenderActionPayload (or maybe do it in the adapter's SendablePayloadFactory)
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
