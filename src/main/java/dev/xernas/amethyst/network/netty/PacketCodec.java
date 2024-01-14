package dev.xernas.amethyst.network.netty;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketRegistry;
import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;
import java.util.Map;

public class PacketCodec extends ByteToMessageCodec<IPacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacket iPacket, ByteBuf out) throws Exception {
        MCByteBuf byteBuf = new MCByteBuf(out);
        byteBuf.writeVarInt(PacketRegistry.getId(iPacket.getClass()));
        iPacket.write(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes() > 0) {
            MCByteBuf mcByteBuf = new MCByteBuf(byteBuf);
            int len = mcByteBuf.readVarInt();
            int id = mcByteBuf.readVarInt();

            IPacket packet = PacketRegistry.getById(id);
            if (packet != null) {
                out.add(packet);
                PacketInHandler.currentByteBuf = mcByteBuf;
            }
        }
    }
}
