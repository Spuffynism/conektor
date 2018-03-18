package xyz.ndlr.model.dispatching;

public enum SupportedProvider {
    FACEBOOK("facebook", 1),
    IMGUR("imgur", 4),
    TRELLO("trello", 2),
    SSH("ssh", 3);

    private final String value;
    /**
     * id used in the database
     */
    private final int providerId;

    SupportedProvider(String value, int providerId) {
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

    public int getProviderId() {
        return providerId;
    }
}
