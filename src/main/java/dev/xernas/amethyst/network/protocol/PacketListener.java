package dev.xernas.amethyst.network.protocol;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public interface PacketListener {

    void handlePacket(PacketReader reader, NetworkManager networkManager) throws IOException;

}
