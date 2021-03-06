package com.worthto.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author gezz
 * @description
 * @date 2020/2/25.
 */
public class ChatClient {
    public static void main(String[] args) {
        EventLoopGroup eventGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventGroup).channel(NioSocketChannel.class).handler(new ChatClientInitializer());
            Channel channel = bootstrap.connect("localhost", 9001).sync().channel();
//            channel.closeFuture().sync();

            while(true) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String msg = bufferedReader.readLine() + "\r\n";
//                String msg = "心跳...";
                channel.writeAndFlush(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventGroup.shutdownGracefully();
        }
    }
}
