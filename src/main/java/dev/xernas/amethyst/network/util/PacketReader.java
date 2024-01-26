package dev.xernas.amethyst.network.util;

import dev.xernas.amethyst.network.protocol.IPacket;

import java.io.IOException;
import java.util.Map;

public class PacketReader {

    private final IPacket packet;
    private final Map<String, Object> readMap;

    public PacketReader(IPacket packet, MCByteBuf byteBuf) throws IOException {
        this.packet = packet;
        this.readMap = packet.read(byteBuf);
    }

    public boolean is(Class<? extends IPacket> clazz) {
        return packet.getClass() == clazz;
    }

    public <T> T read(String key) {
        return (T) readMap.get(key);
    }
}
