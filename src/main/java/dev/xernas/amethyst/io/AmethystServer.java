package dev.xernas.amethyst.io;

import dev.xernas.amethyst.ServerConstants;
import dev.xernas.amethyst.io.models.GameProfile;
import dev.xernas.amethyst.io.netty.PacketCodec;
import dev.xernas.amethyst.io.netty.PacketInHandler;
import dev.xernas.amethyst.io.protocol.PacketRegistry;
import dev.xernas.amethyst.io.util.EncryptionGenerator;
import dev.xernas.amethyst.logging.AmethystConsole;
import dev.xernas.amethyst.logging.AmethystFormatter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.logging.*;

public class AmethystServer implements Runnable {

    private static final Logger logger = Logger.getLogger("Amethyst");
    private static final EncryptionGenerator generator = new EncryptionGenerator();;
    private static GameProfile gameProfile;

    public AmethystServer() throws IOException {
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
        FileHandler fileHandler = new FileHandler("log.txt");
        logger.addHandler(fileHandler);
        AmethystFormatter formatter = new AmethystFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(new AmethystConsole(formatter));
        logger.log(Level.WARNING, "Amethyst Server starting...");
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("packetCodec", new PacketCodec());
                            socketChannel.pipeline().addLast("handler", new PacketInHandler());
                        }
                    });

            PacketRegistry.setup();
            logger.info("Listening on port: " + ServerConstants.port);
            ChannelFuture future = server.bind(ServerConstants.port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static EncryptionGenerator getGenerator() {
        return generator;
    }

    public static void setGameProfile(GameProfile gameProfile) {
        AmethystServer.gameProfile = gameProfile;
    }

    public static GameProfile getGameProfile() {
        return gameProfile;
    }
}
