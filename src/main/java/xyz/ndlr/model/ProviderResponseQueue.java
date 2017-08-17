package xyz.ndlr.model;

import org.springframework.stereotype.Component;
import xyz.ndlr.model.provider.ProviderResponse;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ProviderResponseQueue extends LinkedBlockingQueue<ProviderResponse> {
}
