package dev.xernas.amethyst.network.protocol.play;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class PlayListener implements PacketListener {
    @Override
    public void handlePacket(PacketReader reader, NetworkManager networkManager) throws IOException {

    }
}
