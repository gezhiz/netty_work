package com.worthto.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author gezz
 * @description
 * @date 2020/3/1.
 */
public class NioTest6 {

    /**
     * 内存映射文件，使用的是堆外内存
     * @throws IOException
     */
    @Test
    public void testRandomAccessFile() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("random.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(4, (byte) 'a');
        randomAccessFile.close();
    }

    /**
     * 文件锁
     */
    @Test
    public void testFileLock() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("fileLock.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.lock(3,6,true);
        System.out.println("fileLock is valid:" + fileLock.isValid());
        System.out.println("lock type is shared: " + fileLock.isShared());
    }

    @Test
    public void testSocketClient() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9001));
        socket.getOutputStream().write('1');
    }

    /**
     * 分散、汇集
     * scattering: channel读取字节到buffer数组中
     * gathering: buffer数组写入到channel中
     */
    @Test
    public void testScatterAndGather() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(9001));

        int msgLength1 = 2;
        int msgLength2 = 3;
        int msgLength3 = 4;
        int msgLength = msgLength1 + msgLength2 + msgLength3;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(msgLength1);
        byteBuffers[1] = ByteBuffer.allocate(msgLength2);
        byteBuffers[2] = ByteBuffer.allocate(msgLength3);

        SocketChannel channel = socketChannel.accept();
        while (true) {
            int byteRead = 0;
            while(byteRead < msgLength) {
                long result = channel.read(byteBuffers);
                System.out.println("bytes read : " + result);
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position" + byteBuffer.position()
                        + ", limit" + byteBuffer.limit()).forEach(System.out :: println);
            }

            Arrays.asList(byteBuffers).forEach(
                    byteWritten -> byteWritten.flip()
            );
            long byteWritten = 0;
            while(byteWritten < msgLength) {
                long writeResult = channel.write(byteBuffers);
                byteWritten += writeResult;
            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("byte Read :" + byteRead + "byte write :" + byteWritten + "msg length :" + msgLength);
        }
    }
}
