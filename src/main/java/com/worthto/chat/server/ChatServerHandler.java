package com.worthto.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 处理http请求
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String>{

    /**
     * 用来保存Channel对象
     */
    private final static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 收到消息 messageReceived
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        final Channel channel = channelHandlerContext.channel();
        if (StringUtils.isNotBlank(msg)) {
            channelGroup.forEach(ch -> {
                if (channel != ch) {
                    //不发送消息给自己
                    ch.writeAndFlush("from server : channel " + channel.remoteAddress() + "发送消息：" + msg);
                } else {
                    channel.writeAndFlush("from server; 消息已传达:" + msg);
                }
            });
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("from server : " + channel.remoteAddress() + "已接入");
        channelGroup.add(channel);
        System.out.println("from server : " + channel.remoteAddress() + "已接入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("from server : " + channel.remoteAddress() + "已离开");
        System.out.println("server log : " + channel.remoteAddress() + "已离开");
        //remove的时候无需调用channelGroup的remove方法
        System.out.println("server log : 当前拥有" + channelGroup.size() + "个客户端已连接");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush("from server : 欢迎你" + channel.remoteAddress());
        channelGroup.writeAndFlush("from server : " + channel.remoteAddress() + "已上线");
        System.out.println("from server : " + channel.remoteAddress() + "已上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("client : " + channel.remoteAddress() + "已下线");
        System.out.println("from server : " + channel.remoteAddress() + "已下线");
    }
}
