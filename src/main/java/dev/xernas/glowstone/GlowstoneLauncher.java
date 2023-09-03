package dev.xernas.glowstone;

import dev.xernas.glowstone.io.GlowstoneServer;

public class GlowstoneLauncher {

    public static void main(String[] args) {

        GlowstoneServer server = new GlowstoneServer();
        Thread serverThread = new Thread(server, "Glowstone-Server");
        serverThread.start();
    }

}