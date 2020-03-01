package com.worthto.nio;

import org.junit.Test;
import sun.nio.ch.SelectionKeyImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author gezz
 * @description
 * @date 2020/3/1.
 */
public class NioServer {



    private static final Map<String,SocketChannel> clientSocketMap = new HashMap();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(9001));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("监听端口事件");
        while (true) {
            int selectedCount = selector.select();
            if (selectedCount > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    SocketChannel client = null;
                    if (selectionKey.isAcceptable()) {
                        try {
                            //处理连接对象
                            ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                            serverChannel.configureBlocking(false);
                            client = serverChannel.accept();
                            String key = "" + UUID.randomUUID();
                            //把client端的socket暂存在map中
                            client.configureBlocking(false);
                            clientSocketMap.put(key,client);
                            client.register(selector, SelectionKeyImpl.OP_READ);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (selectionKey.isReadable()) {
                        //处理可读事件
                        try {
                            client = (SocketChannel) selectionKey.channel();
                            client.configureBlocking(false);
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            readBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String receiveMsg = String.valueOf(charset.decode(readBuffer));
//                            System.out.println(client.socket().getRemoteSocketAddress() + "message:" + receiveMsg);

                            String sendUUID = "";
                            for (Map.Entry<String,SocketChannel> entry : clientSocketMap.entrySet()) {
                                if (client == entry.getValue()) {
                                    sendUUID = entry.getKey();
                                    System.out.println(sendUUID + ":" + receiveMsg);
                                    break;
                                }
                            }
                            for (Map.Entry<String,SocketChannel> entry : clientSocketMap.entrySet()) {
                                SocketChannel socketChannel = entry.getValue();
                                //开启一个新的缓冲器，写数据到channel
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((sendUUID + ":" + receiveMsg).getBytes());
                                writeBuffer.flip();
                                socketChannel.write(writeBuffer);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                if (client != null) {
                                    client.close();
                                    clientSocketMap.remove(client);
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }


                    } else if (selectionKey.isWritable()) {

                    }
                    selectionKeys.remove(selectionKey);

                });
            }
        }
    }
}
