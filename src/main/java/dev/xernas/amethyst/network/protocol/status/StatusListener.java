package dev.xernas.amethyst.network.protocol.status;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Map;

public class StatusListener implements PacketListener {

    @Override
    public void handlePacket(PacketReader reader, NetworkManager networkManager) throws IOException {
        if (reader.is(PingPongPacket.class)) {
            long timestamp = reader.read("timestamp");
            networkManager.sendPacket(new PingPongPacket(timestamp));
        }
        if (reader.is(StatusPacket.class)) {
            networkManager.sendPacket(new StatusPacket());
        }
    }

}
