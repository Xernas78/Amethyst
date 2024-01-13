package dev.xernas.amethyst.network.handlers;

import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketInHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        MCByteBuf mcByteBuf = new MCByteBuf(byteBuf);
        System.out.println("Received new packet");
        System.out.println("Size: " + mcByteBuf.readVarInt());
        System.out.println("ID: " + mcByteBuf.readVarInt());
    }
}
