package dev.xernas.amethyst.network.netty;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public class PacketInHandler extends SimpleChannelInboundHandler<IPacket> {

    public static MCByteBuf currentByteBuf;
    private final NetworkManager manager;

    public PacketInHandler(NetworkManager manager) {
        this.manager = manager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        manager.setNextState(0);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IPacket packet) throws Exception {
        System.out.println("Receiving packet: " + packet.getClass().getSimpleName());
        PacketListener listener = manager.getListener();
        manager.setContext(channelHandlerContext);
        if (listener != null) {
            listener.handlePacket(new PacketReader(packet, currentByteBuf), manager);
        } else {
            manager.setNextState(0);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
