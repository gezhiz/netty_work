package com.worthto.compose.client;

import com.worthto.compose.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author gezz
 * @description
 * @date 2020/2/27.
 */
public class ProtobufClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person person) throws Exception {
        System.out.println("address:" + person.getAddress());
        System.out.println("name:" + person.getName());
        System.out.println("age:" + person.getAge());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setAddress("北京市朝阳区")
                .setName("gezz")
                .setAge(18)
                .build();
        ctx.channel().writeAndFlush(person);
    }
}
