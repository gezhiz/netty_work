package com.worthto.socket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 处理http请求
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<String>{

    /**
     * 收到消息 messageReceived
     * @param channelHandlerContext
     * @param httpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String httpObject) throws Exception {
        System.out.println("channel address" + channelHandlerContext.channel().remoteAddress() + "msg:" + httpObject);
        channelHandlerContext.channel().writeAndFlush(" from server " + UUID.randomUUID());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
