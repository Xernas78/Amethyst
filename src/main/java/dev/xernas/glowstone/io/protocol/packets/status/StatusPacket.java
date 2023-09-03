package dev.xernas.glowstone.io.protocol.packets.status;

import com.google.gson.Gson;
import dev.xernas.glowstone.ServerConstants;
import dev.xernas.glowstone.chat.TextChatComponent;
import dev.xernas.glowstone.io.models.ServerInfoResponse;
import dev.xernas.glowstone.io.protocol.IPacket;
import dev.xernas.glowstone.io.util.MCByteBuf;
import dev.xernas.glowstone.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class StatusPacket implements IPacket {

    private ServerInfoResponse serverInfos;

    public StatusPacket() {

    }

    public StatusPacket(ServerInfoResponse serverInfoResponse) {
        this.serverInfos = serverInfoResponse;
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        return new HashMap<>();
    }

    @Override
    public void write(MCByteBuf byteBuf) {
        byteBuf.writeUTF(new Gson().toJson(serverInfos, ServerInfoResponse.class));
    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new StatusPacket(ServerConstants.serverInfo));
    }
}
