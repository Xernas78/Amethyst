package dev.xernas.amethyst.io.protocol.packets.status;

import com.google.gson.Gson;
import dev.xernas.amethyst.ServerConstants;
import dev.xernas.amethyst.io.models.ServerInfoResponse;
import dev.xernas.amethyst.io.protocol.IPacket;
import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.util.StateManager;
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
