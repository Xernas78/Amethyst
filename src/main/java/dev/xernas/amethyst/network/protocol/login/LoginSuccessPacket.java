package dev.xernas.amethyst.network.protocol.login;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginSuccessPacket implements IPacket {

    private String username;
    private UUID uuid;

    public LoginSuccessPacket(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        return new HashMap<>();
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeUUID(uuid);
        byteBuf.writeString(username);
        byteBuf.writeVarInt(0);
    }
}
