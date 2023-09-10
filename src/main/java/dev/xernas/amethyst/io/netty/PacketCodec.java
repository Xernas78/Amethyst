package dev.xernas.amethyst.io.netty;

import dev.xernas.amethyst.io.protocol.IPacket;
import dev.xernas.amethyst.io.protocol.PacketRegistry;
import dev.xernas.amethyst.io.util.Direction;
import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.util.StateManager;
import dev.xernas.amethyst.utils.AmethystLogger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class PacketCodec extends ByteToMessageCodec<IPacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IPacket iPacket, ByteBuf byteBuf) throws Exception {
        MCByteBuf mcByteBuf = new MCByteBuf(byteBuf);
        MCByteBuf temp = new MCByteBuf(Unpooled.buffer());
        try {
            temp.writeVarInt(PacketRegistry.getId(PacketInHandler.getStateManager().getCurrentState(), Direction.CLIENTBOUND, iPacket));
            iPacket.write(temp);
            byte[] bytes = new byte[temp.getByteBuf().readableBytes()];
            temp.getByteBuf().readBytes(bytes);
            mcByteBuf.writeVarInt(bytes.length);
            mcByteBuf.getByteBuf().writeBytes(bytes);
        } catch (NullPointerException ex) {
            AmethystLogger.warn("Une erreur est survenue avec le packet " + iPacket.getClass().getSimpleName());
            ex.printStackTrace();
        }

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        StateManager stateManager = PacketInHandler.getStateManager();
        MCByteBuf mcByteBuf = new MCByteBuf(byteBuf);
        int size = mcByteBuf.readVarInt();
        int id = mcByteBuf.readVarInt();

        try {
            IPacket packet = PacketRegistry.getPacket(stateManager.getCurrentState(), id, Direction.SERVERBOUND);
            packet.handle(new MCByteBuf(byteBuf), stateManager, PacketInHandler.getReadCtx());
            list.add(packet);
        } catch (NullPointerException e) {

        }



    }
}
