package dev.xernas.amethyst.io.util;

import dev.xernas.amethyst.io.models.GameProfile;
import dev.xernas.amethyst.io.models.Property;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public void writeUUID(UUID uuid) {
        byteBuf.writeLong(uuid.getMostSignificantBits());
        byteBuf.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID() {
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }

    public void writeProperty(Property property) {
        writeUTF(property.getName());
        writeUTF(property.getValue());
        byteBuf.writeBoolean(property.isHasSignature());
        if (property.isHasSignature()) {
            writeUTF(property.getSignature());
        }
    }

    public Property readProperty() {
        String name = readUTF();
        String value = readUTF();
        boolean hasSign = byteBuf.readBoolean();
        if (hasSign) {
            return new Property(name, value, readUTF());
        }
        else {
            return new Property(name, value);
        }
    }

    public void writePropertyArray(List<Property> properties) {
        writeVarInt(properties.size());
        for (int i = 0; i < properties.size(); i++) {
            writeProperty(properties.get(i));
        }
    }

    public List<Property> readPropertyArray() {
        List<Property> properties = new ArrayList<>();
        int size = readVarInt();
        for (int i = 0; i < size; i++) {
            properties.add(readProperty());
        }
        return properties;
    }

    public void writeGameProfile(GameProfile profile) {
        writeUUID(profile.getUuid());
        writeUTF(profile.getUsername());
        writePropertyArray(profile.getProperties());
    }

    public GameProfile readGameProfile() {
        UUID uuid = readUUID();
        String username = readUTF();
        List<Property> properties = readPropertyArray();
        GameProfile gameProfile = new GameProfile(uuid, username);
        gameProfile.setProperties(properties);
        return gameProfile;
    }

}
