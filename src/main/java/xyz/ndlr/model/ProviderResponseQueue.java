package xyz.ndlr.model;

import xyz.ndlr.model.provider.ProviderResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ProviderResponseQueue extends LinkedBlockingQueue<ProviderResponse> {
}
