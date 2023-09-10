package dev.xernas.amethyst.io.protocol.packets.handshake;

import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.protocol.IPacket;
import dev.xernas.amethyst.io.util.State;
import dev.xernas.amethyst.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class HandshakePacket implements IPacket {

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        Map<String, Object> map = new HashMap<>();
        map.put("protocol", byteBuf.readVarInt());
        map.put("host", byteBuf.readUTF());
        map.put("port", byteBuf.getByteBuf().readUnsignedShort());
        map.put("state", byteBuf.readVarInt());
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) {

    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {
        int nextState = (int) read(byteBuf).get("state");
        if (nextState == 1) {
            stateManager.changeState(State.STATUS);

        } else if (nextState == 2) {
            stateManager.changeState(State.LOGIN);
        }
    }

}
