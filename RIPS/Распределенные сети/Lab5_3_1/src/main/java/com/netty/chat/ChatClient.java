package com.netty.chat;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClient {

    static final String HOST = "localhost";
    static final int PORT = 8080;
    static String clientName;
    static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws Exception {
        System.out.println("Enter your name: ");
        clientName = scanner.nextLine();

        clientStart();
    }

    private static void clientStart() throws InterruptedException {
        var group = new NioEventLoopGroup();
        try {
            ChannelFuture f = new Bootstrap().group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), new ChatClientHandler());
                        }
                    })
                    .connect(HOST, PORT).sync();

            f.sync().channel().writeAndFlush("[" + clientName + "]: " + scanner.nextLine() + "\r");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
