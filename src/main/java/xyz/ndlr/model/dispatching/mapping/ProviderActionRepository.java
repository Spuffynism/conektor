package xyz.ndlr.model.dispatching.mapping;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

@Component
public class ProviderActionRepository {
    private Table<String, String, Action> actions;

    public ProviderActionRepository() {
        actions = HashBasedTable.create();
    }

    public void register(String provider, String actionName, Action action) {
        actions.put(provider, actionName, action);
    }

    public Action get(String provider, String actionName) {
        return actions.get(provider, actionName);
    }
}
