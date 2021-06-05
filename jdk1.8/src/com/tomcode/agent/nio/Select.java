package com.tomcode.agent.nio;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Select {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(8888));

        Selector selector = Selector.open();
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT); // 接收连接请求


        while(true) {
            if(selector.select(8000) == 0){
                // 等待获取5000ms，没有事件
                System.out.println("8s没有事件");
                continue;
            }

            System.out.println("event!");

            // 获取并遍历有事件发生的SelectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 证明是ServerSocketChannel，获取对应的SocketChannel
                    SocketChannel socket = server.accept();
                    socket.configureBlocking(false);
                    // 向selector注册socket，为读操作事件(因为建立连接不一定马上有数据传输)
                    socket.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    // 证明是SocketChannel
                    SocketChannel socket = (SocketChannel) key.channel();
                    ByteBuffer buf = (ByteBuffer) key.attachment();
                    socket.read(buf);

                    String content = new String(buf.array());
                    System.out.println("收到:" + content);
                }

                // 处理必须从集合中移除，否则会重复处理
                iterator.remove();
            }
        }
    }
}
