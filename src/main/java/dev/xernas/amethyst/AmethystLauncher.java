package dev.xernas.amethyst;

import dev.xernas.amethyst.io.AmethystServer;
import org.apache.commons.lang3.SystemUtils;
import org.fusesource.jansi.AnsiConsole;

public class AmethystLauncher {

    public static void main(String[] args) {
        if (SystemUtils.IS_OS_WINDOWS) {
            AnsiConsole.systemInstall();
        }

        AmethystServer server = new AmethystServer();
        Thread serverThread = new Thread(server, "Amethyst-Server");
        serverThread.start();
    }

}