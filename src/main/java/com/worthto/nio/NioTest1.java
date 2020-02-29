package com.worthto.nio;

import org.junit.Test;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * @author gezz
 * @description
 * @date 2020/2/29.
 */
public class NioTest1 {


    @Test
    public void testIntBuffer() {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0; i < intBuffer.capacity(); i ++) {
            intBuffer.put(new Random().nextInt(100));
        }
        //Flips this buffer.  The limit is set to the current position and then the position is set to zero.
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
