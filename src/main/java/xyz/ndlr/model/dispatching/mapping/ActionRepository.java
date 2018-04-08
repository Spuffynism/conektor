package xyz.ndlr.model.dispatching.mapping;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all provider actions.
 */
@Component
public class ActionRepository {
    /**
     * All available provider actions.
     * Is populated during the application bootstrapping step.
     */
    private final Table<String, String, Action> actions;

    /**
     *
     */
    private final Map<String, String> providerHumanNames;

    public ActionRepository() {
        actions = HashBasedTable.create();
        providerHumanNames = new HashMap<>();
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

    public Action getDefault(String provider) {
        return actions.get(provider, ActionMapping.DEFAULT_ACTION);
    }

    public void registerHumanName(String provider, String providerHumanName) {
        providerHumanNames.put(provider, providerHumanName);
    }

    public String getProviderHumanName(String provider) {
        return providerHumanNames.get(provider);
    }

    public Map<String, String> getProviderHumanNames() {
        return providerHumanNames;
    }
}
