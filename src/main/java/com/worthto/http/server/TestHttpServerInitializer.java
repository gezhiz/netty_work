package com.worthto.http.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 链接被初始化后的一个回调器
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class TestHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        channelPipeline.addLast("httpServerCodec", new HttpServerCodec());
        //自定义处理器
        channelPipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
    }
}
