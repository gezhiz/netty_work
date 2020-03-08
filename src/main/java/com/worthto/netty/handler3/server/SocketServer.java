package com.worthto.netty.handler3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketServer {
    public static void main(String[] args) {
        int threadCount = 2;
        //worker处理用户的链接
        EventLoopGroup workGroup = new NioEventLoopGroup(threadCount);
        //boss接受客户端的链接,然后交给workGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(threadCount);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new SocketServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(9001).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
