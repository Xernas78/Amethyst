package dev.xernas.glowstone.io.protocol.packets.login;

import dev.xernas.glowstone.io.models.GameProfile;
import dev.xernas.glowstone.io.protocol.IPacket;
import dev.xernas.glowstone.io.util.MCByteBuf;
import dev.xernas.glowstone.io.util.State;
import dev.xernas.glowstone.io.util.StateManager;
import dev.xernas.glowstone.utils.GlowstoneLogger;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
