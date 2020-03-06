package com.worthto.bytebuf;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * @author gezz
 * @description
 * @date 2020/3/5.
 */
public class ByteBufTest {

    @Test
    public void testByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i = 0; i< byteBuf.capacity(); i++) {
            if (byteBuf.maxWritableBytes() >= 4) {
                byteBuf.writeByte(i);
            }
        }
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }

    }

    @Test
    public void testCharSet() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world !", CharsetUtil.UTF_8);

        //DirectBiteBuf 直接内存不是通过字节数组的方式实现false
        // HeapByteBuf 堆内存使用的是字节数组实现返回true
        if (byteBuf.hasArray()) {
            byte[] bytes = byteBuf.array();
            String content = new String(bytes, CharsetUtil.UTF_8);
            System.out.println(content);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            int length = byteBuf.readableBytes();
            System.out.println(length);

            for (;byteBuf.isReadable();) {
                System.out.println((char)byteBuf.readByte());
            }

            //读完了，只是索引发生变化，内容还在
            System.out.println(byteBuf.getCharSequence(0, 5, CharsetUtil.UTF_8));
            System.out.println(byteBuf.getCharSequence(4, 7, CharsetUtil.UTF_8));

        }

    }

    @Test
    public void testCompositBuf() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf byteBuf = Unpooled.buffer(10);
        ByteBuf directBuffer = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(byteBuf, directBuffer);
        Iterator iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
