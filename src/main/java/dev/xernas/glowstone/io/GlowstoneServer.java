package dev.xernas.glowstone.io;

import dev.xernas.glowstone.ServerConstants;
import dev.xernas.glowstone.io.models.ServerInfoResponse;
import dev.xernas.glowstone.io.netty.PacketCodec;
import dev.xernas.glowstone.io.netty.PacketInHandler;
import dev.xernas.glowstone.io.protocol.PacketRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class GlowstoneServer implements Runnable {
    public GlowstoneServer() {

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
            ChannelFuture future = server.bind(ServerConstants.port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
