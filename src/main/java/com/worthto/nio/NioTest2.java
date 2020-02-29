package com.worthto.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * @author gezz
 * @description
 * @date 2020/2/29.
 */
public class NioTest2 {


    @Test
    public void testIO() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/gezz/IdeaProjects/netty_work/src/main/java/com/worthto/nio/NioTest2.java");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(5012);
        channel.read(byteBuffer);

        byteBuffer.flip();
        //切换到写
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            System.out.println("character:" + (char)b);
        }

        fileInputStream.close();

    }
}