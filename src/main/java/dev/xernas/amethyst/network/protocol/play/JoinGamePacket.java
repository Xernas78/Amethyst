package dev.xernas.amethyst.network.protocol.play;

import dev.xernas.amethyst.model.Identifier;
import dev.xernas.amethyst.model.Position;
import dev.xernas.amethyst.model.Types;
import dev.xernas.amethyst.network.protocol.IPacket;
import dev.xernas.amethyst.network.util.MCByteBuf;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinGamePacket implements IPacket {

    private int entityId;
    private boolean isHardcore;
    private List<Identifier> dimensions;
    private int maxPlayers;
    private int renderDistance;
    private int simDistance;
    private boolean reducedDebugInfo;
    private boolean respawnScreen;
    private boolean doLimitedCrafting;
    private Identifier dimensionType;
    private Identifier dimensionName;
    private long hashedSeed;
    private byte gameMode;
    private byte previousGameMode;
    private boolean isDebug;
    private boolean isFlat;
    private boolean hasDeathLocation;
    private Identifier deathDimensionName;
    private Position deathLocation;
    private int portalCooldown;

    public JoinGamePacket(List<Identifier> dimensions, int maxPlayers, Identifier dimensionType, Identifier dimensionName, long hashedSeed, boolean hasDeathLocation, Identifier deathDimensionName, Position deathLocation) {
        this.entityId = 0;
        this.isHardcore = false;
        this.dimensions = dimensions;
        this.maxPlayers = maxPlayers;
        this.renderDistance = 6;
        this.simDistance = 8;
        this.reducedDebugInfo = false;
        this.respawnScreen = true;
        this.doLimitedCrafting = false;
        this.dimensionType = dimensionType;
        this.dimensionName = dimensionName;
        this.hashedSeed = hashedSeed;
        this.gameMode = 0;
        this.previousGameMode = 0;
        this.isDebug = false;
        this.isFlat = false;
        this.hasDeathLocation = hasDeathLocation;
        this.deathDimensionName = deathDimensionName;
        this.deathLocation = deathLocation;
        this.portalCooldown = 0;
    }

    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) throws IOException {
        return new HashMap<>();
    }

    @Override
    public void write(MCByteBuf byteBuf) throws IOException {
        byteBuf.writeVarInt(entityId);
        byteBuf.getByteBuf().writeBoolean(isHardcore);
        byteBuf.writeList(dimensions, Types.IDENTIFIER);
        byteBuf.writeVarInt(maxPlayers);
        byteBuf.writeVarInt(renderDistance);
        byteBuf.writeVarInt(simDistance);
        byteBuf.getByteBuf().writeBoolean(reducedDebugInfo);
        byteBuf.getByteBuf().writeBoolean(respawnScreen);
        byteBuf.getByteBuf().writeBoolean(doLimitedCrafting);
        byteBuf.writeIdentifier(dimensionType);
        byteBuf.writeIdentifier(dimensionName);
        byteBuf.getByteBuf().writeLong(hashedSeed);
        byteBuf.getByteBuf().writeByte(gameMode);
        byteBuf.getByteBuf().writeByte(previousGameMode);
        byteBuf.getByteBuf().writeBoolean(isDebug);
        byteBuf.getByteBuf().writeBoolean(isFlat);
        byteBuf.getByteBuf().writeBoolean(hasDeathLocation);
        if (hasDeathLocation) {
            byteBuf.writeIdentifier(deathDimensionName);
        }
        byteBuf.writeVarInt(portalCooldown);
    }
}
