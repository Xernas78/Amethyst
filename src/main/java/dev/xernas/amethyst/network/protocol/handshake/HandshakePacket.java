package dev.xernas.amethyst.network.protocol.handshake;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandshakePacket implements IPacket {
    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("protocol", byteBuf.readVarInt());
        map.put("host", byteBuf.readString());
        map.put("port", byteBuf.getByteBuf().readUnsignedShort());
        map.put("state", byteBuf.readVarInt());
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) {

    }
}
