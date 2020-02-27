package com.worthto.compose.many.server;

import com.worthto.compose.protobuf.ManyData;
import com.worthto.compose.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gezz
 * @description
 * @date 2020/2/24.
 */
public class ProtobufManyDataServerHandler extends SimpleChannelInboundHandler<ManyData.MyObject> {

    /**
     * 收到消息 messageReceived
     * @param ctx
     * @param myObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ManyData.MyObject myObject) throws Exception {
        if (myObject.getDataType() == ManyData.MyObject.DataType.CatType) {
            ManyData.Cat cat = myObject.getCat();
            System.out.println("cat name:" + cat.getName());
            System.out.println("cat age:" + cat.getAge());
            System.out.println("cat address:" + cat.getAddress());
        } else if (myObject.getDataType() == ManyData.MyObject.DataType.PersonType) {
            ManyData.Person person = myObject.getPerson();
            System.out.println("person name:" + person.getName());
            System.out.println("person age:" + person.getAge());
            System.out.println("person address:" + person.getAddress());
        } else if (myObject.getDataType() == ManyData.MyObject.DataType.DogType) {
            ManyData.Dog dog = myObject.getDog();
            System.out.println("dog name:" + dog.getName());
            System.out.println("dog age:" + dog.getAge());
            System.out.println("dog address:" + dog.getAddress());
        } else {
            throw new IllegalArgumentException("错误的类型");
        }
        ctx.writeAndFlush(myObject);
    }

}
