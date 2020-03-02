package com.worthto.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author gezz
 * @description
 * @date 2020/3/2.
 */
public class OldClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9001);
        String filename = "/Users/gezz/dev/58/soft/XMind.8.pro.3.7.8.wlrjy.dmg";
        InputStream inputStream = new FileInputStream(filename);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        int readCount = 0;
        int total = 0;
        long startTime = System.currentTimeMillis();
        byte[] readBytes = new byte[4096];
        while ((readCount = inputStream.read(readBytes)) > 0) {
            total += readCount;
            dataOutputStream.write(readBytes);
        }
        System.out.println("发送字节数：" + total + "时间：" + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        inputStream.close();
        socket.close();

    }
}
