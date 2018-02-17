package xyz.ndlr.security.auth;

public enum Role {
    NONE (0b0, "No roles"),
    USER (0b1, "Basic user role - can only manipulate ones belongings"),
    ADMIN (0b10, "Basic admin role - can do everything");

    private final int value;
    private final String description;

    Role(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isContainedIn(int permissions) {
        return (permissions & value) == value;
    }

    /**
     * Adds a permission mask to a mask composed of many masks (permissions).
     * If a permission mask is already present in permissions, permissions will not change.
     *
     * 0b0100 | 0b0010 -> 0b0110
     * 0b0110 | 0b0010 -> 0b0110
     *
     * @param permissions a number representing many masks
     * @return the new permissions
     */
    public int addTo(int permissions) {
        return permissions | this.value();
    }

    /**
     * Removes a permission mask from a mask composed of many masks (permissions).
     * If a permission mask is not present in permissions, permissions will not change.
     *
     * 0b0110 ^ (0b0110 & 0b0010) = 0b0100
     * 0b0110 ^ (0b0101 & 0b0010) = 0b0110
     *
     * @param permissions a number representing many masks
     * @return the new permissions
     */
    public int removeFrom(int permissions) {
        return permissions ^ (permissions & this.value());
    }
}
