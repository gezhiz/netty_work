package com.worthto.netty.handler3.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author gezz
 * @description
 * @date 2020/3/7.
 */
public class ContentDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("contentDecoder invoke");

        int length = in.readInt();
        byte[] bytes = new byte[length];

        in.readBytes(bytes);
        ContentProtocol  contentProtocol = new ContentProtocol();

        contentProtocol.setContent(bytes);
        contentProtocol.setLength(length);
        out.add(contentProtocol);
    }
}
