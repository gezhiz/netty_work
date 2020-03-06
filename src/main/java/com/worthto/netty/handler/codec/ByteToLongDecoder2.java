package com.worthto.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author gezz
 * @description
 * @date 2020/3/6.
 */
public class ByteToLongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode called");
        System.out.println("readableBytes:" + in.readableBytes());
//        if (in.readableBytes() >= 8) {
//            out.add(in.readLong());
//        }
        //不需要判断可读字节是否大于等于8
        out.add(in.readLong());

    }
}
