package dev.xernas.amethyst.network.protocol.configuration;

import dev.xernas.amethyst.model.Identifier;
import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.protocol.play.JoinGamePacket;
import dev.xernas.amethyst.network.util.MCByteBuf;
import dev.xernas.amethyst.network.util.PacketReader;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationListener implements PacketListener {
    @Override
    public void handlePacket(PacketReader reader, NetworkManager networkManager) throws IOException {
        if (reader.is(FinishConfigurationPacket.class)) {
            networkManager.setNextState(4);
            List<Identifier> dimensions = new ArrayList<>();
            dimensions.add(new Identifier("minecraft", "overworld"));
            dimensions.add(new Identifier("minecraft", "the_nether"));
            dimensions.add(new Identifier("minecraft", "the_end"));
            networkManager.sendPacket(new JoinGamePacket(dimensions, 10, dimensions.get(0), dimensions.get(0), 11, false, null, null));
        }
    }
}
