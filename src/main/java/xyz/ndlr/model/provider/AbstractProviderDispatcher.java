package xyz.ndlr.model.provider;

import xyz.ndlr.model.dispatching.IMessage;
import xyz.ndlr.model.entity.User;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Provider's individual dispatcher
 */
public abstract class AbstractProviderDispatcher<T extends IMessage> {
    public abstract CompletableFuture<ProviderResponse> dispatch(User user, T message)
            throws IllegalArgumentException;

    protected static <E extends Enum<E>> E getFirstAction(Map<String, String> arguments,
                                                          Class<E> action) {
        Map.Entry<String, String> firstEntry = arguments.entrySet().iterator().next();
        E firstAction;

        try {
            firstAction = Enum.valueOf(action, firstEntry.getKey().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("unknown action");
        }

        return firstAction;
    }
}
