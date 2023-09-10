package dev.xernas.amethyst.io.protocol.packets.login;

import dev.xernas.amethyst.io.models.GameProfile;
import dev.xernas.amethyst.io.protocol.IPacket;
import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public class LoginSuccessPacket implements IPacket {

    private GameProfile gameProfile;

    public LoginSuccessPacket() {

    }

    public LoginSuccessPacket(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        return null;
    }

    @Override
    public void write(MCByteBuf byteBuf) {
        byteBuf.writeGameProfile(gameProfile);
    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {

    }
}
