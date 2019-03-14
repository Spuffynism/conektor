package xyz.ndlr.domain.dispatching.mapping;

import java.util.Map;

public interface IActionRepository {
    void register(String provider, String actionName, Action action);

    Action get(String provider, String actionName);

    Action getDefault(String provider);

    void registerHumanName(String provider, String providerHumanName);

    /**
     * @return an immutable provider human names mapping
     */
    Map<String, String> getProviderHumanNames();

    void registerImageProviderHumanName(String provider, String providerHumanName);

    /**
     * @return an immutable image provider human names mapping
     */
    Map<String, String> getImageProviderHumanNames();
}
