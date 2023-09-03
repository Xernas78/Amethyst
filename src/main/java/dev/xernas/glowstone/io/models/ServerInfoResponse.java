package dev.xernas.glowstone.io.models;

import dev.xernas.glowstone.chat.ChatComponent;

public class ServerInfoResponse {

    private final Version version;
    private final Players players;
    private final ChatComponent description;
    private final String favicon;

    public ServerInfoResponse(Version version, Players players, ChatComponent description, String favicon) {
        this.version = version;
        this.players = players;
        this.description = description;
        this.favicon = favicon;
    }

    public Version getVersion() {
        return version;
    }

    public Players getPlayers() {
        return players;
    }

    public ChatComponent getDescription() {
        return description;
    }

    public String getFavicon() {
        return favicon;
    }

    public static class Version {

        private final String name;
        private final int protocol;

        public Version(String name, int protocol) {
            this.name = name;
            this.protocol = protocol;
        }

        public String getName() {
            return name;
        }

        public int getProtocol() {
            return protocol;
        }
    }

    public static class Players {

        private final int max;
        private final int online;

        public Players(int max, int online) {
            this.max = max;
            this.online = online;
        }

        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }
    }

}
