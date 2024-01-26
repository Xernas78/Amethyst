package dev.xernas.amethyst.network.protocol.login;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.protocol.configuration.FinishConfigurationPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class LoginListener implements PacketListener {

    @Override
    public void handlePacket(PacketReader reader, NetworkManager networkManager) throws IOException {
        if (reader.is(LoginStartPacket.class)) {
            String username = reader.read("username");
            UUID uuid = reader.read("uuid");
            System.out.println(username);
            System.out.println(uuid);
            networkManager.sendPacket(new LoginSuccessPacket(username, uuid));
        }
        if (reader.is(LoginAknowledgedPacket.class)) {
            networkManager.setNextState(3);
            networkManager.sendPacket(new FinishConfigurationPacket());
        }
    }

}
