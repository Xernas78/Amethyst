package dev.xernas.amethyst.io.protocol.packets.login;

import dev.xernas.amethyst.chat.TextChatComponent;
import dev.xernas.amethyst.io.AmethystServer;
import dev.xernas.amethyst.io.models.GameProfile;
import dev.xernas.amethyst.io.protocol.IPacket;
import dev.xernas.amethyst.io.util.EncryptionGenerator;
import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.util.State;
import dev.xernas.amethyst.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginStartPacket implements IPacket {

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", byteBuf.readUTF(16));
        if (byteBuf.getByteBuf().readBoolean()) {
            map.put("uuid", byteBuf.readUUID());
        } else {
            map.put("uuid", null);
        }
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) {

    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {
        Map<String, Object> response = read(byteBuf);
        String username = (String) response.get("username");
        UUID uuid = (UUID) response.get("uuid");
        if (uuid == null) {
            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
        }

        AmethystServer.setGameProfile(new GameProfile(uuid, username));
        //ctx.writeAndFlush(new EncryptionPacket(AmethystServer.getGenerator().getKeyPair(), AmethystServer.getGenerator().getVerifyToken()));
        ctx.writeAndFlush(new LoginSuccessPacket(AmethystServer.getGameProfile()));
        stateManager.changeState(State.PLAY);
    }
}
