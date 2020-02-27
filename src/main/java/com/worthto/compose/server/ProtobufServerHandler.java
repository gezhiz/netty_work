package com.worthto.compose.server;

import com.worthto.compose.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理http请求
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class ProtobufServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

    /**
     * 收到消息 messageReceived
     * @param ctx
     * @param person
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person person) throws Exception {
        System.out.println("address:" + person.getAddress());
        System.out.println("name:" + person.getName());
        System.out.println("age:" + person.getAge());
        ctx.writeAndFlush(person);

    }

}
