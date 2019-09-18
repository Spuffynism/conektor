package xyz.ndlr.infrastructure;

import org.springframework.stereotype.Component;
import xyz.ndlr.domain.provider.ProviderResponse;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ProviderResponseQueue extends LinkedBlockingQueue<ProviderResponse> {
}
