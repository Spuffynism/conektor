package xyz.ndlr.infrastructure.dispatching;

import xyz.ndlr.domain.provider.ProviderId;

public enum SupportedProvider {
    FACEBOOK("facebook", ProviderId.from(1)),
    IMGUR("imgur", ProviderId.from(4)),
    TRELLO("trello", ProviderId.from(2)),
    SSH("ssh", ProviderId.from(3));

    private final String value;
    /**
     * id used in the database
     */
    private final ProviderId providerId;

    SupportedProvider(String value, ProviderId providerId) {
        this.value = value;
        this.providerId = providerId;
    }

    public static SupportedProvider tryGetProvider(String provider) {
        SupportedProvider supportedProvider;

        try {
            supportedProvider = SupportedProvider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("unknown provider");
        }

        return supportedProvider;
    }

    public String value() {
        return value;
    }

    public ProviderId getProviderId() {
        return providerId;
    }
}
