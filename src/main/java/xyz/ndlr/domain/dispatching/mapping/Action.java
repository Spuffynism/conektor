package xyz.ndlr.domain.dispatching.mapping;

import xyz.ndlr.domain.entity.User;
import xyz.ndlr.domain.provider.ProviderResponse;
import xyz.ndlr.domain.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

/**
 * An action that can be made to a provider
 * <p>
 * ex.: adding a list to a trello board, uploading a picture
 */
public interface Action extends BiFunction<User, PipelinedMessage, ProviderResponse> {
}
