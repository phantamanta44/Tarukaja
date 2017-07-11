package io.github.phantamanta44.tarukaja.util;

import io.netty.buffer.ByteBuf;

import java.util.stream.IntStream;

public class LowUtils {

    public static void writeStr(ByteBuf buf, String string) {
        buf.writeShort(string.length());
        string.codePoints().forEach(buf::writeInt);
    }

    public static String readStr(ByteBuf buf) {
        return IntStream.generate(buf::readInt)
                .limit(buf.readShort())
                .collect(StringBuffer::new, StringBuffer::appendCodePoint, StringBuffer::append)
                .toString();
    }

}
