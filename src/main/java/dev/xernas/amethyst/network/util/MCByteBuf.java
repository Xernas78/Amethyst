package dev.xernas.amethyst.network.util;

import dev.xernas.amethyst.model.Identifier;
import dev.xernas.amethyst.model.Types;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MCByteBuf {

    private final ByteBuf byteBuf;

    public MCByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public int readVarInt() throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = byteBuf.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new IOException("VarInt is too big");
        }

        return value;
    }

    public int peekVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;
        int index = 0;
        do {
            currentByte = byteBuf.getByte(byteBuf.readerIndex() + index++);
            value |= (currentByte & SEGMENT_BITS) << position;

            if (position >= 32) throw new RuntimeException("VarInt is too big");

            position += 7;
        } while ((currentByte & 0b10000000) != 0);

        return value;
    }

    public void writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                byteBuf.writeByte(value);
                return;
            }

            byteBuf.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

            value >>>= 7;
        }
    }

    public String readString() throws IOException {
        int len = readVarInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void writeString(String str) throws IOException {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        if (bytes.length >= 32767) {
            throw new IOException("String too long");
        } else {
            writeVarInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }

    public UUID readUUID() {
        long mostSign = byteBuf.readLong();
        long leastSign = byteBuf.readLong();
        return new UUID(mostSign, leastSign);
    }

    public void writeUUID(UUID uuid) {
        long mostSign = uuid.getMostSignificantBits();
        long leastSign = uuid.getLeastSignificantBits();
        byteBuf.writeLong(mostSign);
        byteBuf.writeLong(leastSign);
    }

    public Identifier readIdentifier() throws IOException {
        return Identifier.fromString(readString());
    }

    public void writeIdentifier(Identifier identifier) throws IOException {
        writeString(identifier.toString());
    }

    public <E> List<E> readList(Types type) throws IOException {
        int len = readVarInt();
        List<E> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            if (type.equals(Types.STRING)) {
                list.add((E) readString());
            }
            if (type.equals(Types.IDENTIFIER)) {
                list.add((E) readIdentifier());
            }
        }
        return list;
    }

    public <E> void writeList(List<E> list, Types type) throws IOException {
        writeVarInt(list.size());
        for (E e : list) {
            if (type.equals(Types.STRING)) {
                writeString((String) e);
            }
            if (type.equals(Types.IDENTIFIER)) {
                writeIdentifier((Identifier) e);
            }
        }
    }

}
