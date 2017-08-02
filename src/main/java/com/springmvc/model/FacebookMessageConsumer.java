package com.springmvc.model;

import com.springmvc.exception.CannotSendMessageException;
import com.springmvc.model.dispatching.ProviderResponseToSendPayloadAdapter;
import com.springmvc.model.provider.ProviderResponse;
import com.springmvc.model.provider.facebook.FacebookMessageSender;
import com.springmvc.model.provider.facebook.sendAPI.SendablePayload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class FacebookMessageConsumer implements Runnable {
    private static final Logger logger = Logger.getLogger(FacebookMessageConsumer.class);

    private final BlockingQueue<ProviderResponse> sharedResponseQueue;
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
