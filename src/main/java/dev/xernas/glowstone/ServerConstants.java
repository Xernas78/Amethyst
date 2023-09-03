package dev.xernas.glowstone;

import dev.xernas.glowstone.chat.TextChatComponent;
import dev.xernas.glowstone.io.models.ServerInfoResponse;

public class ServerConstants {

    public static int port = 25565;
    public static ServerInfoResponse serverInfo = new ServerInfoResponse(
            new ServerInfoResponse.Version("Glowstone 1.20.1", 763),
            new ServerInfoResponse.Players(1, 1),
            new TextChatComponent("Hey ! Faut que je travaille ce motd"),
            ""
    );

}
