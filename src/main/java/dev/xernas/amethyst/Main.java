package dev.xernas.amethyst;

import dev.xernas.amethyst.network.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(25565);
        try {
            server.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
