package com.worthto.netty.handler2.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //每当有一个客户端链接时都会调用一次
        System.out.println("初始化 server initializer");
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast(new SocketServerHandler());
    }

}
