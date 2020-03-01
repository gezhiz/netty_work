package com.worthto.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author gezz
 * @description
 * @date 2020/2/29.
 */
public class NioTest5 {

    /**
     * 堆外内存
     * @throws IOException
     */
    @Test
    public void testDirectBuffer() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");


        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(512);
        while (true) {
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            outputChannel.write(byteBuffer);

        }
    }


    @Test
    public void testIO() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(10);
        byteBuffer.putLong(123124410L);
        byteBuffer.putDouble(102353.234324D);
        byteBuffer.putChar('a');
        byteBuffer.putShort((short) 1);

        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }

    @Test
    public void test() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.position(2);
        byteBuffer.limit(7);

        //byteBuffer 2-7 的内容与sliceBuffer 共享底层数组
        ByteBuffer sliceBuffer = byteBuffer.slice();
        System.out.println("newBuffer == byteBuffer:" + (byteBuffer == sliceBuffer));
        System.out.println("newBuffer.capacity(): " + sliceBuffer.capacity());

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            sliceBuffer.put(i, (byte)( sliceBuffer.get(i) * 2));
        }

        byteBuffer.flip();
        byteBuffer.limit(byteBuffer.capacity());
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }
    }

    /**
     * 只读buffer
     */
    @Test
    public void testOnlyRead() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        ByteBuffer onlyReadBuffer = byteBuffer.asReadOnlyBuffer();

    }
}