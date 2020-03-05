package com.worthto.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
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
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
//
    private EventLoopGroup eventLoopGroup;

    public ChatServerInitializer() {
        super();
        eventLoopGroup = new NioEventLoopGroup();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //添加分隔符解码器
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new ChatServerHandler());
        //把事件handler交给eventLoopGroup去做，避免阻塞IO线程
        pipeline.addLast(eventLoopGroup,new ChatServerHandler());


    }
}
