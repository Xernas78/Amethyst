package dev.xernas.amethyst.network.protocol.status;

import dev.xernas.amethyst.network.StateManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Map;

public class StatusListener implements PacketListener {

    @Override
    public void handlePacket(IPacket packet, MCByteBuf byteBuf, ChannelHandlerContext ctx) throws IOException {
        Map<String, Object> map = packet.read(byteBuf);
        if (packet instanceof PingPongPacket) {
            long timestamp = (long) map.get("timestamp");
            ctx.writeAndFlush(new PingPongPacket(timestamp));
        }
        if (packet instanceof StatusPacket) {
            ctx.writeAndFlush(new StatusPacket());
        }
    }

}
