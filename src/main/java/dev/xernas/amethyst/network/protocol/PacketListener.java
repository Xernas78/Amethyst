package dev.xernas.amethyst.network.protocol;

import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public interface PacketListener {

    void handlePacket(IPacket packet, MCByteBuf byteBuf, ChannelHandlerContext ctx) throws IOException;

}
