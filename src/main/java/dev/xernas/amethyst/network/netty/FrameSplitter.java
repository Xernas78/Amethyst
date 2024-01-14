package dev.xernas.amethyst.network.netty;

import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class FrameSplitter extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        MCByteBuf mcByteBuf = new MCByteBuf(in);
        do {
            final int len = mcByteBuf.peekVarInt();
            if (len > in.readableBytes()) {
                throw new IllegalStateException(len + " > " + in.readableBytes());
            }

            // Read the bytes that are part of this frame and add them to the result
            final ByteBuf byteBuf = in.readBytes(len);
            out.add(byteBuf.retain());
        } while (in.readableBytes() > 0);
    }
}
