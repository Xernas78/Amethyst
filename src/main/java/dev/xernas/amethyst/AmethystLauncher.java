package dev.xernas.amethyst;

import dev.xernas.amethyst.io.AmethystServer;

import java.io.IOException;

public class AmethystLauncher {

    public static void main(String[] args) {
        try {
            AmethystServer server = new AmethystServer();
            Thread serverThread = new Thread(server, "Amethyst-Server");
            serverThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}