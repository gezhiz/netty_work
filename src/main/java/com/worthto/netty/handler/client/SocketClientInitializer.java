package com.worthto.netty.handler.client;

import com.worthto.netty.handler.codec.ByteToLongDecoder;
import com.worthto.netty.handler.codec.ByteToLongDecoder2;
import com.worthto.netty.handler.codec.LongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

//        channelPipeline.addLast(new ByteToLongDecoder());
        channelPipeline.addLast(new ByteToLongDecoder2());
        channelPipeline.addLast(new LongToByteEncoder());

        channelPipeline.addLast(new SocketClientHandler());
    }
}
