package dev.xernas.amethyst.network;

import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.protocol.PacketListener;
import dev.xernas.amethyst.network.protocol.configuration.ConfigurationListener;
import dev.xernas.amethyst.network.protocol.handshake.HandshakeListener;
import dev.xernas.amethyst.network.protocol.login.LoginListener;
import dev.xernas.amethyst.network.protocol.play.PlayListener;
import dev.xernas.amethyst.network.protocol.status.StatusListener;
import io.netty.channel.ChannelHandlerContext;

public class NetworkManager {

    private ChannelHandlerContext context;

    private int state = 0;

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    public int getState() {
        return state;
    }

    public void setNextState(int state) {
        System.out.println("Changing state to " + state);
        this.state = state;
    }

    public PacketListener getListener() {
        return switch (state) {
            case 0 -> new HandshakeListener();
            case 1 -> new StatusListener();
            case 2 -> new LoginListener();
            case 3 -> new ConfigurationListener();
            case 4 -> new PlayListener();
            default -> null;
        };
    }

    public void sendPacket(IPacket packet) {
        context.writeAndFlush(packet);
    }
}
