package com.tomcode.agent.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.socket().connect(new InetSocketAddress("127.0.0.1", 8888));

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8888));
        OutputStream outputStream = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String content = scanner.nextLine();

            if("exit".equals(content)) {
                client.close();
                break;
            }

            byte[] cs = content.getBytes();
            ByteBuffer buf = ByteBuffer.allocate(cs.length);
            buf.put(cs);

            outputStream.write(cs);
            outputStream.flush();
        }

    }
}
