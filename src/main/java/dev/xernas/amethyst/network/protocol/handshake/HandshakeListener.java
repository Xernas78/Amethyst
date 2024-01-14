package dev.xernas.amethyst.network.protocol.handshake;

import dev.xernas.amethyst.network.StateManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Map;

public class HandshakeListener implements PacketListener {
    @Override
    public void handlePacket(IPacket packet, MCByteBuf byteBuf, ChannelHandlerContext ctx) throws IOException {
        Map<String, Object> map = packet.read(byteBuf);
        int protocol = (int) map.get("protocol");
        String host = (String) map.get("host");
        int port = (int) map.get("port");
        int state = (int) map.get("state");
        StateManager.setNextState(state);
    }
}
