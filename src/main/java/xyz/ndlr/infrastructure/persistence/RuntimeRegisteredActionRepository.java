package xyz.ndlr.infrastructure.persistence;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;
import xyz.ndlr.infrastructure.mapping.Action;
import xyz.ndlr.infrastructure.mapping.ActionMapping;
import xyz.ndlr.infrastructure.mapping.ActionMappingMethodCallback;
import xyz.ndlr.infrastructure.mapping.ActionRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds all provider actions.
 */
@Component
public class RuntimeRegisteredActionRepository implements ActionRepository {
    /**
     * All available provider actions.
     * Is populated during the application bootstrapping step by
     * {@link ActionMappingMethodCallback}.
     */
    private final Table<String, String, Action> actions;

    /**
     * Mapping of provider names to their human names
     */
    private final Map<String, String> providerHumanNames;

    /**
     * Mapping of image provider names to their human names;
     */
    private final Map<String, String> imageProviderHumanNames;

    public RuntimeRegisteredActionRepository() {
        actions = HashBasedTable.create();
        providerHumanNames = new HashMap<>();
        imageProviderHumanNames = new HashMap<>();
    }

    /**
     * Registers an action
     *
     * @param provider   name of the provider
     * @param actionName name of the action
     * @param action     the registered action
     */
    @Override
    public void register(String provider, String actionName, Action action) {
        actions.put(provider, actionName, action);
    }

    @Override
    public Action get(String provider, String actionName) {
        return actions.get(provider, actionName);
    }

    @Override
    public Action getDefault(String provider) {
        return actions.get(provider, ActionMapping.DEFAULT_ACTION);
    }

    @Override
    public void registerHumanName(String provider, String providerHumanName) {
        providerHumanNames.put(provider, providerHumanName);
    }

    /**
     * @return an immutable provider human names mapping
     */
    @Override
    public Map<String, String> getProviderHumanNames() {
        return Collections.unmodifiableMap(providerHumanNames);
    }

    @Override
    public void registerImageProviderHumanName(String provider, String providerHumanName) {
        imageProviderHumanNames.put(provider, providerHumanName);
    }

    /**
     * @return an immutable image provider human names mapping
     */
    public Map<String, String> getImageProviderHumanNames() {
        return Collections.unmodifiableMap(imageProviderHumanNames);
    }
}
