package com.springmvc.model.provider;

import java.util.List;
import java.util.function.Function;

/**
 * Describes an action that can be made to a provider
 *
 * @param <T> the action's input type
 * @param <U> the action's output type
 */
public class Action<T extends AbstractProviderArgument, U extends IProviderResponse> {
    private Function<T, U> action;
    private U actionResult;
    private Action parent;
    private List<Action> children;

    private boolean isRoot() {
        return parent == null;
    }

    private boolean isLeaf() {
        return action != null;
    }
}
