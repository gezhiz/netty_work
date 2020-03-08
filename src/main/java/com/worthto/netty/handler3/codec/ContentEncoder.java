package com.worthto.netty.handler3.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author gezz
 * @description
 * @date 2020/3/7.
 */
public class ContentEncoder extends MessageToByteEncoder<ContentProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ContentProtocol msg, ByteBuf out) throws Exception {
        System.out.println("content encoder invoke");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());


    }
}
