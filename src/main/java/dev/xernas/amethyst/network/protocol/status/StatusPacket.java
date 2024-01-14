package dev.xernas.amethyst.network.protocol.status;

import com.google.gson.JsonObject;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatusPacket implements IPacket {
    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        return new HashMap<>();
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        JsonObject object = new JsonObject();
        JsonObject version = new JsonObject();
        version.addProperty("name", "1.20.2");
        version.addProperty("protocol", 764);
        JsonObject players = new JsonObject();
        players.addProperty("max", 10);
        players.addProperty("online", 1);
        JsonObject description = new JsonObject();
        description.addProperty("text", "Salut !");
        object.add("version", version);
        object.add("players", players);
        object.add("description", description);
        byteBuf.writeString(object.toString());
    }
}
