package dev.xernas.amethyst.network.protocol.login;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginAknowledgedPacket implements IPacket {

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        return new HashMap<>();
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {

    }
}
