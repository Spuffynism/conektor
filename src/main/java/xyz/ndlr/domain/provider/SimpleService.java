package xyz.ndlr.domain.provider;

import xyz.ndlr.domain.user.User;
import xyz.ndlr.infrastructure.mapping.ActionMapping;
import xyz.ndlr.infrastructure.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

/**
 * Represents services which have only one action.
 */
public interface SimpleService extends BiFunction<User, PipelinedMessage, ProviderResponse> {

    @ActionMapping(ActionMapping.DEFAULT_ACTION)
    @Override
    ProviderResponse apply(User user, PipelinedMessage pipelinedMessage);
}
