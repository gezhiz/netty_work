package com.worthto.netty.handler2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理http请求
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<ByteBuf>{

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 收到消息 messageReceived
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //目标缓冲区bytes长度不能大于源缓冲区byteBuf
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String msg = new String(bytes, CharsetUtil.UTF_8);
        System.out.println("server received:" + msg);
        int count = atomicInteger.get();
        while(!atomicInteger.compareAndSet(count, count + 1)) {}
        System.out.println("接收到消息数量:" + atomicInteger.get());
        ByteBuf responseBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(responseBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
