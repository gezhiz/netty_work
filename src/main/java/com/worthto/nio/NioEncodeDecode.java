package com.worthto.nio;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * nio 编码 解码
 * @author gezz
 * @description
 * @date 2020/3/1.
 */
public class NioEncodeDecode {

    @Test
    public void test() throws IOException {
        String inputFile = "encodeInput.txt";
        String outFile = "decodeOutput.txt";
        RandomAccessFile inputAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputAccessFile = new RandomAccessFile(outFile, "rw");
        long fileLength = new File(inputFile).length();

        FileChannel inputChannel = inputAccessFile.getChannel();
        FileChannel outputChannel = outputAccessFile.getChannel();

        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
        MappedByteBuffer outputData = outputChannel.map(FileChannel.MapMode.READ_WRITE,0, fileLength);

        ByteBuffer byteBuffer = inputData.asReadOnlyBuffer();

        //思考，为何iso字符集，还是无中文乱码
        Charset charset = Charset.forName("iso-8859-1");

        CharBuffer charBuffer = charset.decode(byteBuffer);

        ByteBuffer byteBuffer1 = charset.encode(charBuffer);

//        outputData.put(byteBuffer1);
        outputChannel.write(byteBuffer1);

        inputAccessFile.close();
        outputAccessFile.close();

    }
}
