package dev.xernas.glowstone.io.protocol.packets.status;

import dev.xernas.glowstone.io.protocol.IPacket;
import dev.xernas.glowstone.io.util.MCByteBuf;
import dev.xernas.glowstone.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class PingPongPacket implements IPacket {

    private Long time;

    public PingPongPacket() {

    }

    public PingPongPacket(Long time) {
        this.time = time;
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        Map<String, Object> map = new HashMap<>();
        map.put("time", byteBuf.getByteBuf().readLong());
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) {
        byteBuf.getByteBuf().writeLong(time);
    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {
        Long time = (Long) read(byteBuf).get("time");
        ctx.writeAndFlush(new PingPongPacket(time));
    }
}
