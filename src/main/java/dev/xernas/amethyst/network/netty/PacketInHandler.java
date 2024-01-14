package dev.xernas.amethyst.network.netty;

import dev.xernas.amethyst.network.StateManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.protocol.PacketRegistry;
import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketInHandler extends SimpleChannelInboundHandler<IPacket> {

    public static MCByteBuf currentByteBuf;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StateManager.setNextState(0);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IPacket packet) throws Exception {
        System.out.println("STATE: " + StateManager.getState());
        System.out.println("Received new packet with ID: " + PacketRegistry.getId(packet.getClass()));
        PacketListener listener = StateManager.getListener();
        if (listener != null) {
            listener.handlePacket(packet, currentByteBuf, channelHandlerContext);
        } else {
            System.out.println("No states");
            StateManager.setNextState(0);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
