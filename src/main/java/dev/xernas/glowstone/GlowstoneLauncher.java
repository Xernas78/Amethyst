package dev.xernas.glowstone;

import dev.xernas.glowstone.io.GlowstoneServer;
import org.apache.commons.lang3.SystemUtils;
import org.fusesource.jansi.AnsiConsole;

public class GlowstoneLauncher {

    public static void main(String[] args) {
        if (SystemUtils.IS_OS_WINDOWS) {
            AnsiConsole.systemInstall();
        }

        GlowstoneServer server = new GlowstoneServer();
        Thread serverThread = new Thread(server, "Glowstone-Server");
        serverThread.start();
    }

}