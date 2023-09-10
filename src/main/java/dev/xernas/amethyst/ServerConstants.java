package dev.xernas.amethyst;

import dev.xernas.amethyst.chat.TextChatComponent;
import dev.xernas.amethyst.io.models.ServerInfoResponse;

public class ServerConstants {

    public static int port = 25565;
    public static ServerInfoResponse serverInfo = new ServerInfoResponse(
            new ServerInfoResponse.Version("Amethyst 1.20.1", 763),
            new ServerInfoResponse.Players(1, 1),
            new TextChatComponent("Hey ! Faut que je travaille ce motd"),
            ""
    );

}
