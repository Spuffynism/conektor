package com.springmvc.model;

public enum Permission {
    NONE (0x0, "No permissions"),
    USER (0x1, "Basic user role"), // 2^0
    ADMIN (0x2, "Basic admin role"); // 2^1

    private final int value;
    private final String description;

    Permission(int value, String description) {
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
     * 0100 | 0010 -> 0110
     * 0110 | 0010 -> 0110
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
     * 0110 ^ (0110 & 0010) = 0100
     * 0110 ^ (0101 & 0010) = 0110
     *
     * @param permissions a number representing many masks
     * @return the new permissions
     */
    public int removeFrom(int permissions) {
        return permissions ^ (permissions & this.value());
    }
}
