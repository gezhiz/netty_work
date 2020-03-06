package com.worthto.netty.handler2.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 制造粘包和拆包
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    private AtomicInteger atomicInteger = new AtomicInteger(0);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("sent from client", CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

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
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
