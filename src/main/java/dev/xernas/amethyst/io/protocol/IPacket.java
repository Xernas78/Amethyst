package dev.xernas.amethyst.io.protocol;

import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public interface IPacket {

    Map<String, Object> read(MCByteBuf byteBuf);

    void write(MCByteBuf byteBuf);

    void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx);

}
