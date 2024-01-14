package dev.xernas.amethyst.network;

import dev.xernas.amethyst.network.netty.PacketCodec;
import dev.xernas.amethyst.network.netty.PacketInHandler;
import dev.xernas.amethyst.network.netty.PacketLength;
import dev.xernas.amethyst.network.protocol.PacketRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("length", new PacketLength());
                            socketChannel.pipeline().addLast("codec", new PacketCodec());
                            socketChannel.pipeline().addLast("handler", new PacketInHandler());
                        }
                    })
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture f = b.bind(port).sync();

            setup();


            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private void setup() {
        System.out.println("Listening on port " + port);
        PacketRegistry.registerPackets();
    }

}
