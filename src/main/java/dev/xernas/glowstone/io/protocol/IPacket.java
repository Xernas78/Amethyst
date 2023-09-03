package dev.xernas.glowstone.io.protocol;

import dev.xernas.glowstone.io.util.MCByteBuf;
import dev.xernas.glowstone.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public interface IPacket {

    Map<String, Object> read(MCByteBuf byteBuf);

    void write(MCByteBuf byteBuf);

    void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx);

}
