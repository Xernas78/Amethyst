package dev.xernas.amethyst.network;

import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.protocol.handshake.HandshakeListener;
import dev.xernas.amethyst.network.protocol.login.LoginListener;
import dev.xernas.amethyst.network.protocol.status.StatusListener;

public class StateManager {

    private static int state = 0;

    public static int getState() {
        return state;
    }

    public static void setNextState(int state) {
        StateManager.state = state;
    }

    public static PacketListener getListener() {
        return switch (state) {
            case 0 -> new HandshakeListener();
            case 1 -> new StatusListener();
            case 2 -> new LoginListener();
            default -> null;
        };
    }
}
