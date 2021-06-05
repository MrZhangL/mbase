package com.tomcode.agent.nio;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class Buf {

    public static void main(String[] args) throws IOException {
        FileChannel open = FileChannel.open(new File("sad").toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE);
        MappedByteBuffer map = open.map(FileChannel.MapMode.READ_WRITE, 0, open.size());

        map.putChar('d');

    }
}
