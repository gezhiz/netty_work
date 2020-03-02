package com.worthto.zerocopy;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author gezz
 * @description
 * @date 2020/3/2.
 */
public class OldServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9001));
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream outputStream = new DataInputStream(socket.getInputStream());
            byte[] readBytes = new byte[4096];
            int total = 0;
            while (true) {
                int readCount = outputStream.read(readBytes);
                if (readCount <= 0) {
                    break;
                }
                total += readCount;
            }
            System.out.println("共接收到：" + total + "字节");
        }
    }
}
