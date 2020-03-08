package com.worthto.netty.handler3.client;

import com.worthto.netty.handler3.codec.ContentDecoder;
import com.worthto.netty.handler3.codec.ContentEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline channelPipeline = ch.pipeline();
        //自定义处理器
        channelPipeline.addLast(new ContentDecoder());
        channelPipeline.addLast(new ContentEncoder());
        channelPipeline.addLast(new SocketClientHandler());

    }
}
