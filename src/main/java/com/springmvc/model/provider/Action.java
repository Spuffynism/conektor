package com.springmvc.model.provider;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Describes an action that can be made to a provider
 *
 * @param <T> the action's output type
 */
public class Action<T extends IProviderResponse> {
    private Function<Map<String, String>, T> action;
    private T actionResult;
    private Action parent;
    private List<Action> children;

    private boolean isRoot() {
        return parent == null;
    }

    private boolean isLeaf() {
        return action != null;
    }
}
