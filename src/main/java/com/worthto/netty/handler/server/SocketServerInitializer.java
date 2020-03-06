package com.worthto.netty.handler.server;

import com.worthto.netty.handler.codec.ByteToLongDecoder2;
import com.worthto.netty.handler.codec.LongToByteEncoder;
import com.worthto.netty.handler.codec.LongToStringDecoder;
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
        ChannelPipeline channelPipeline = ch.pipeline();

//        channelPipeline.addLast(new ByteToLongDecoder());
        channelPipeline.addLast(new ByteToLongDecoder2());
        channelPipeline.addLast(new LongToStringDecoder());
        channelPipeline.addLast(new LongToByteEncoder());
        //自定义处理器
        channelPipeline.addLast(new SocketServerHandler());
    }

}
