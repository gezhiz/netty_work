package com.worthto.compose.many.client;

import com.worthto.compose.protobuf.ManyData;
import com.worthto.compose.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author gezz
 * @description
 * @date 2020/2/27.
 */
public class ProtobufManyDataHandler extends SimpleChannelInboundHandler<ManyData.MyObject> {

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
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int num = new Random().nextInt(2);
        ManyData.MyObject myObject = null;
        if (num == 0) {
            myObject =  ManyData.MyObject.newBuilder()
                    .setDataType(ManyData.MyObject.DataType.PersonType)
                    .setPerson(ManyData.Person.newBuilder().setName("gezz").setAddress("北京朝阳").setAge(20).build())
                    .build();

        } else if (num == 1) {
            myObject =  ManyData.MyObject.newBuilder()
                    .setDataType(ManyData.MyObject.DataType.DogType)
                    .setDog(ManyData.Dog.newBuilder().setName("liuxi").setAddress("北京朝阳").setAge(20).build())
                    .build();

        } else if (num == 2) {
            myObject =  ManyData.MyObject.newBuilder()
                    .setDataType(ManyData.MyObject.DataType.CatType)
                    .setCat(ManyData.Cat.newBuilder().setName("cat").setAddress("北京朝阳").setAge(20).build())
                    .build();

        }
        ctx.writeAndFlush(myObject);

    }
}
