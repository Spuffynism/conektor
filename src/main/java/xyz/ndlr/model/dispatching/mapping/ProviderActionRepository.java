package xyz.ndlr.model.dispatching.mapping;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

@Component
public class ProviderActionRepository {
    private Table<String, String, BiFunction<User, PipelinedMessage, ProviderResponse>> actions;

    public ProviderActionRepository() {
        actions = HashBasedTable.create();
    }

    public void register(String provider, String action,
                         BiFunction<User, PipelinedMessage, ProviderResponse> method) {
        actions.put(provider, action, method);
    }

    public void deregister(String provider, String action) {
        actions.remove(provider, action);
    }

    public BiFunction<User, PipelinedMessage, ProviderResponse> getAction(String provider,
                                                                          String action) {
        return actions.get(provider, action);
    }
}
