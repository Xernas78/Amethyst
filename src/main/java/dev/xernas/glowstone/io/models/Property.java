package dev.xernas.glowstone.io.models;

import javax.annotation.Nonnull;

public class Property {

    private String name;
    private String value;
    private boolean hasSignature;
    private String signature;

    public Property(String name, String value, @Nonnull String signature) {
        this.name = name;
        this.value = value;
        this.hasSignature = true;
        this.signature = signature;
    }

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
        this.hasSignature = false;
        this.signature = null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isHasSignature() {
        return hasSignature;
    }

    public String getSignature() {
        return signature;
    }
}
