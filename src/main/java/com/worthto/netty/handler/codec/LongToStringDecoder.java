package com.worthto.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * LineBasedFrameDecoder        Both {@code "\n"} and {@code "\r\n"} are handled.
 * FixedLengthFrameDecoder      固定长度为一帧进行解析
 * DelimiterBasedFrameDecoder   根据特定字符作为定界符进行解析
 * LengthFieldBasedFrameDecoder  基于长度字段来解析侦
 * Long 转String的解码器
 * @author gezz
 * @description
 * @date 2020/3/6.
 */
public class LongToStringDecoder extends MessageToMessageDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("message to message decoder:" + msg);
        String msgStr = msg.toString();
        out.add(msgStr);
    }


}
