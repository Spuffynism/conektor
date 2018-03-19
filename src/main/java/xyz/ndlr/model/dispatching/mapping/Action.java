package xyz.ndlr.model.dispatching.mapping;

import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.util.function.BiFunction;

/**
 * Represents an action that can be made to a provider:
 * ex.: adding a list to a trello board, uploading a picture
 */
public interface Action extends BiFunction<User, PipelinedMessage, ProviderResponse> {
}
