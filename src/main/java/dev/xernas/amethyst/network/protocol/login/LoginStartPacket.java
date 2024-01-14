package dev.xernas.amethyst.network.protocol.login;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginStartPacket implements IPacket {

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("username", byteBuf.readString());
        map.put("uuid", byteBuf.readUUID());
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {

    }
}
