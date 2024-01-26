package dev.xernas.amethyst.model;

import java.util.Locale;

public class Identifier {

    private final String namespace;
    private final String key;

    public Identifier(String key) {
        this("minecraft", key);
    }

    public Identifier(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

    public static Identifier fromString(String string) {
        String[] identifierArray = string.split(":");
        String namespace = identifierArray[0];
        String key = identifierArray[1];
        return new Identifier(namespace, key);
    }

    public String getNamespace() {
        return namespace.toLowerCase(Locale.ROOT);
    }

    public String getKey() {
        return key.toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return getNamespace() + ":" + getKey();
    }
}
