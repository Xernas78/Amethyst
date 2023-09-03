package dev.xernas.glowstone.io.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class MCByteBuf{

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final ByteBuf byteBuf;

    public MCByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public void writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                byteBuf.writeByte(value);
                return;
            }

            byteBuf.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = byteBuf.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public void writeUTF(String s, int i) {
        if (s.length() > i) {
            int j = s.length();

            System.out.println("String too big (was " + j + " characters, max " + i + ")");
        } else {
            byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
            int k = i * 3;

            if (abyte.length > k) {
                System.out.println("String too big (was " + abyte.length + " bytes encoded, max " + k + ")");
            } else {
                writeVarInt(abyte.length);
                byteBuf.writeBytes(abyte);
            }
        }
    }

    public void writeUTF(String s) {
        writeUTF(s, 32767);
    }

    public String readUTF(int limit) {
        int size = readVarInt();
        if (size > limit * 4) {
            System.out.println("String too long (" + size + " > " + limit + " * 4)");
        }
        String string = byteBuf.readCharSequence(size, StandardCharsets.UTF_8).toString();
        if (string.length() > limit) {
            System.out.println("String too long (" + string.length() + " > " + limit + ")");
        }
        return string;
    }

    public String readUTF() {
        return readUTF(32767);
    }

}
