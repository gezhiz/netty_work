package com.worthto.socket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 处理http请求
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketClientHandler extends SimpleChannelInboundHandler<String>{

    /**
     * 收到消息 messageReceived
     * @param channelHandlerContext
     * @param httpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String httpObject) throws Exception {
        System.out.println("client---- : channel address" + channelHandlerContext.channel().remoteAddress() + "msg:" + httpObject);
        channelHandlerContext.writeAndFlush(" from client " + System.currentTimeMillis());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("from client ask!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
