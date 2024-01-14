package dev.xernas.amethyst.network.protocol.login;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.util.MCByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class LoginListener implements PacketListener {

    @Override
    public void handlePacket(IPacket packet, MCByteBuf byteBuf, ChannelHandlerContext ctx) throws IOException {
        if (packet instanceof LoginStartPacket) {
            Map<String, Object> map = packet.read(byteBuf);
            ctx.writeAndFlush(new LoginSuccessPacket((String) map.get("username"), (UUID) map.get("uuid")));
        }
    }

}
