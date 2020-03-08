package com.worthto.netty.handler3.server;

import com.worthto.netty.handler3.codec.ContentProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * 处理http请求
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<ContentProtocol>{

    private int count = 0;
    /**
     * 收到消息 messageReceived
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContentProtocol msg) throws Exception {
        System.out.println("读取到的长度：" +  msg.getLength());
        System.out.println("读取到的内容 : " + new String(msg.getContent(), CharsetUtil.UTF_8));
        System.out.println("服务端接收到消息数量:" + (++count));

        byte[] responseContent = UUID.randomUUID().toString().getBytes(CharsetUtil.UTF_8);
        ContentProtocol contentProtocol = new ContentProtocol();
        contentProtocol.setContent(responseContent);
        contentProtocol.setLength(responseContent.length);
        channelHandlerContext.writeAndFlush(contentProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
