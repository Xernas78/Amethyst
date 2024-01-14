package dev.xernas.amethyst.network.protocol;

import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.Map;

public interface IPacket {

    Map<String, Object> read(MCByteBuf byteBuf) throws IOException;

    void write(MCByteBuf byteBuf) throws IOException;

}
