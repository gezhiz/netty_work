package com.worthto.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gezz
 * @description
 * @date 2020/3/1.
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(9001));
        while(true) {
            int selectCount = selector.select();
            if (selectCount > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    try {
                        if (selectionKey.isConnectable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            if (client.isConnectionPending()) {
                                client.finishConnect();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                                writeBuffer.flip();
                                client.write(writeBuffer);

                                //启动一个线程池接受来自标准输入流的输入
                                ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                                executorService.submit(() -> {
                                    while (true) {
                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String msg = bufferedReader.readLine();
                                        writeBuffer.clear();
                                        writeBuffer.put(msg.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);
                                    }
                                });
                            }
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(byteBuffer);
                            if (count > 0) {
                                byteBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                String msg = String.valueOf(charset.decode(byteBuffer));
                                System.out.println(msg);
                            }
                        }
                        //移除selection
                        selectionKeys.remove(selectionKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

}
