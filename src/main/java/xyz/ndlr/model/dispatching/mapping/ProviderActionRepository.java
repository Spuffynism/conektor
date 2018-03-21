package xyz.ndlr.model.dispatching.mapping;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

/**
 * Holds all provider actions.
 */
@Component
public class ProviderActionRepository {
    /**
     * All available provider actions
     */
    private final Table<String, String, Action> actions;

    public ProviderActionRepository() {
        actions = HashBasedTable.create();
    }

    /**
     * Registers an action
     *
     * @param provider   name of the provider
     * @param actionName name of the action
     * @param action     the registered action
     */
    public void register(String provider, String actionName, Action action) {
        actions.put(provider, actionName, action);
    }

    public Action get(String provider, String actionName) {
        return actions.get(provider, actionName);
    }
}
