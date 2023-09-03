package dev.xernas.glowstone.io.netty;

import dev.xernas.glowstone.io.protocol.packets.handshake.HandshakePacket;
import dev.xernas.glowstone.io.protocol.packets.status.StatusPacket;
import dev.xernas.glowstone.io.util.MCByteBuf;
import dev.xernas.glowstone.io.protocol.IPacket;
import dev.xernas.glowstone.io.util.State;
import dev.xernas.glowstone.io.util.StateManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketInHandler extends SimpleChannelInboundHandler<IPacket> {

    private static StateManager stateManager;
    private static ChannelHandlerContext readCtx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        stateManager = new StateManager();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IPacket packet) throws Exception {
        readCtx = ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    public static StateManager getStateManager() {
        return stateManager;
    }

    public static ChannelHandlerContext getReadCtx() {
        return readCtx;
    }
}
