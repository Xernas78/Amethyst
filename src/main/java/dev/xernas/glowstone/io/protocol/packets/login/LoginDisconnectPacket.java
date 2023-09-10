package dev.xernas.glowstone.io.protocol.packets.login;

import com.google.gson.Gson;
import dev.xernas.glowstone.chat.ChatComponent;
import dev.xernas.glowstone.chat.TextChatComponent;
import dev.xernas.glowstone.io.protocol.IPacket;
import dev.xernas.glowstone.io.util.MCByteBuf;
import dev.xernas.glowstone.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

public class LoginDisconnectPacket implements IPacket {

    private TextChatComponent chatComponent;

    public LoginDisconnectPacket() {

    }

    public LoginDisconnectPacket(TextChatComponent chatComponent) {
        this.chatComponent = chatComponent;
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        return null;
    }

    @Override
    public void write(MCByteBuf byteBuf) {
        byteBuf.writeUTF(new Gson().toJson(chatComponent, ChatComponent.class));
    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {

    }
}
