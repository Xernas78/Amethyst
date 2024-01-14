package dev.xernas.amethyst.network.netty;

import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketLength extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        int len = msg.readableBytes();
        MCByteBuf byteBuf = new MCByteBuf(out);
        byteBuf.writeVarInt(len);
        out.writeBytes(msg);
    }
}
