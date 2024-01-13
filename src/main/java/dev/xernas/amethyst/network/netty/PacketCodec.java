package dev.xernas.amethyst.network.handlers;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketRegistry;
import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class PacketInCodec extends ByteToMessageCodec<IPacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacket iPacket, ByteBuf out) throws Exception {
        MCByteBuf byteBuf = new MCByteBuf(out);
        MCByteBuf data = new MCByteBuf(Unpooled.buffer());
        data.writeVarInt(PacketRegistry.getId(iPacket));
        iPacket.write(data);
        byteBuf.writeVarInt(data.getByteBuf().writableBytes());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {

    }
}
