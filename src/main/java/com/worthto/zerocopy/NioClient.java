package com.worthto.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 采用零拷贝技术
 * @author gezz
 * @description
 * @date 2020/3/2.
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        String filename = "/Users/gezz/dev/58/soft/XMind.8.pro.3.7.8.wlrjy.dmg";
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(9001));
        socketChannel.configureBlocking(true);
        FileChannel fileChannel = new FileInputStream(filename).getChannel();
        long startTime = System.currentTimeMillis();
        //零拷贝，把文件拷贝给socketChannel
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数" + transferCount + "耗时:" + (System.currentTimeMillis() - startTime));
    }
}
