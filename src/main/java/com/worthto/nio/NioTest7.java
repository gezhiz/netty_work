package com.worthto.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author gezz
 * @description
 * @date 2020/3/1.
 */
public class NioTest7 {

    /**
     * 调试方法： 打开终端，使用 命令  nc localhost 9001
     * selector监听多个端口
     * @throws IOException
     */
    @Test
    public void testSelectRegister() throws IOException {

        int[] ports = new int[5];
        ports[0] = 9001;
        ports[1] = 9002;
        ports[2] = 9003;
        ports[3] = 9004;
        ports[4] = 9005;
        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(ports[i]));
            //绑定select,注册 接收链接的事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口：" + ports[i]);
        }
        System.out.println("监听socket请求中。。。");
        while(true) {
            int readyCount = selector.select();
            if (readyCount > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        //处理连接事件
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //获取到客户端的socket
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        //selectionkey使用完之后，一定要remove掉，不然还会遗留在selection set 中
                        iterator.remove();
                        System.out.println("接收到客户端的链接:" + socketChannel.getRemoteAddress());
                    } else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        // 处理读取事件
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int bytesCount = 0;
                        while (true) {
                            int read = socketChannel.read(byteBuffer);
                            if (read <= 0) {
                                break;
                            }
                            byteBuffer.flip();
                            //把内容写回客户端
                            socketChannel.write(byteBuffer);
                            bytesCount += read;
                        }
                        iterator.remove();
                        System.out.println("读取到" + bytesCount + "字节 来自客户端");
                    }
                }
            }
        }

    }
}
