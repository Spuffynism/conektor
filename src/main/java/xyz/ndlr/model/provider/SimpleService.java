package xyz.ndlr.model.provider;

import xyz.ndlr.model.dispatching.mapping.ActionMapping;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

/**
 * Represents services which have only one action.
 */
public interface SimpleService extends BiFunction<User, PipelinedMessage, ProviderResponse> {

    @ActionMapping(ActionMapping.DEFAULT_ACTION)
    @Override
    ProviderResponse apply(User user, PipelinedMessage pipelinedMessage);
}
