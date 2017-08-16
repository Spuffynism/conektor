package xyz.ndlr.model.provider;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Describes an action that can be made to a provider
 */
public class Action {
    private Function<Map<String, String>, ProviderResponse> action;
    private ProviderResponse actionResult;
    private Action parent;
    private List<Action> children;

    private boolean isRoot() {
        return parent == null;
    }

    private boolean isLeaf() {
        return action != null;
    }
}
