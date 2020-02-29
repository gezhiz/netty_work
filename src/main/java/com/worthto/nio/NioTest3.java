package com.worthto.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author gezz
 * @description
 * @date 2020/2/29.
 */
public class NioTest3 {


    @Test
    public void testIO() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("niotest3.txt");

        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byte[] message = "hello world".getBytes();

        for (int i = 0; i < message.length; i++) {
            byteBuffer.put(message[i]);
        }

        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();

    }
}