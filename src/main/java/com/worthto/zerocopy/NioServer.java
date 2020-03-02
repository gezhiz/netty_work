package com.worthto.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 测试零拷贝 省略了selector
 * @author gezz
 * @description
 * @date 2020/3/2.
 */
public class NioServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9001);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(inetSocketAddress);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);
            int readCount = 0;
            int total = 0;

            while((readCount = socketChannel.read(byteBuffer)) != -1) {
                total += readCount;
                byteBuffer.rewind();
            }
            System.out.println("接收到" + total + "字节");
        }
    }

}
