package dev.xernas.amethyst.io.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameProfile {

    private final UUID uuid;
    private final String username;
    private List<Property> properties;

    public GameProfile(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.properties = new ArrayList<>();
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Property> getProperties() {
        return properties;
    }
}
