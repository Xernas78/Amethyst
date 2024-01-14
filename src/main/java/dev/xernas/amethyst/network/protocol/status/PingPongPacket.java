package dev.xernas.amethyst.network.protocol.status;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PingPongPacket implements IPacket {

    private long timestamp;

    public PingPongPacket(long timestamp) {
        this.timestamp = timestamp;
    }
    public PingPongPacket() {}

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", byteBuf.getByteBuf().readLong());
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) {
        byteBuf.getByteBuf().writeLong(timestamp);
    }
}
