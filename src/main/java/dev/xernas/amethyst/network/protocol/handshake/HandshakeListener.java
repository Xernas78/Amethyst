package dev.xernas.amethyst.network.protocol.handshake;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Map;

public class HandshakeListener implements PacketListener {
    @Override
    public void handlePacket(PacketReader reader, NetworkManager networkManager) throws IOException {
        int protocol = reader.read("protocol");
        String host = reader.read("host");
        int port = reader.read("port");
        int state = reader.read("state");
        networkManager.setNextState(state);
    }
}
