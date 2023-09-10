package dev.xernas.amethyst.io.protocol;

import dev.xernas.amethyst.io.protocol.packets.handshake.HandshakePacket;
import dev.xernas.amethyst.io.protocol.packets.login.*;
import dev.xernas.amethyst.io.protocol.packets.status.PingPongPacket;
import dev.xernas.amethyst.io.protocol.packets.status.StatusPacket;
import dev.xernas.amethyst.io.util.Direction;
import dev.xernas.amethyst.io.util.State;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PacketRegistry {
    private final static Map<Map<Direction, Integer>, IPacket> HANDSHAKE_MAP = new HashMap<>();
    private final static Map<Map<Direction, Integer>, IPacket> STATUS_MAP = new HashMap<>();
    private final static Map<Map<Direction, Integer>, IPacket> LOGIN_MAP = new HashMap<>();
    private final static Map<State, Map<Map<Direction, Integer>, IPacket>> PACKET_MAP = new HashMap<>();

    public static void setup() {
        registerPacket(State.HANDSHAKE, Direction.SERVERBOUND, 0x00, new HandshakePacket());
        registerPacket(State.STATUS, Direction.ALL, 0x00, new StatusPacket());
        registerPacket(State.STATUS, Direction.ALL, 0x01, new PingPongPacket());
        registerPacket(State.LOGIN, Direction.SERVERBOUND, 0x00, new LoginStartPacket());
        registerPacket(State.LOGIN, Direction.ALL, 0x01, new EncryptionPacket());
        registerPacket(State.LOGIN, Direction.CLIENTBOUND, 0x02, new LoginSuccessPacket());
        registerPacket(State.LOGIN, Direction.CLIENTBOUND, 0x00, new LoginDisconnectPacket());
        PACKET_MAP.put(State.HANDSHAKE, HANDSHAKE_MAP);
        PACKET_MAP.put(State.STATUS, STATUS_MAP);
        PACKET_MAP.put(State.LOGIN, LOGIN_MAP);
    }

    private static void registerPacket(State state, Direction direction, Integer id, IPacket packet) {
        switch (state) {
            case HANDSHAKE -> HANDSHAKE_MAP.put(toDirectionIdMap(direction, id), packet);
            case STATUS -> STATUS_MAP.put(toDirectionIdMap(direction, id), packet);
            case LOGIN -> LOGIN_MAP.put(toDirectionIdMap(direction, id), packet);
        }
    }

    public static IPacket getPacket(State state, Integer id, Direction direction) throws NullPointerException{
        Map<Map<Direction, Integer>, IPacket> stateMap = PACKET_MAP.get(state);
        IPacket packet = stateMap.get(toDirectionIdMap(direction, id));
        if (packet == null) {
            packet = stateMap.get(toDirectionIdMap(Direction.ALL, id));
        }
        return packet;
    }

    public static Integer getId(State state, Direction direction, IPacket packet) throws NullPointerException{
        Map<Map<Direction, Integer>, IPacket> stateMap = PACKET_MAP.get(state);
        AtomicReference<Integer> finalId = new AtomicReference<>();
        stateMap.forEach((id, ipacket) -> {
            if (ipacket.getClass().getSimpleName().equals(packet.getClass().getSimpleName())) {
                Integer getId = id.get(direction);
                if (getId == null) {
                    getId = id.get(Direction.ALL);
                }
                finalId.set(getId);
            }
        });
        return finalId.get();
    }

    private static Map<Direction, Integer> toDirectionIdMap(Direction direction, Integer id) {
        Map<Direction, Integer> directionIntegerMap = new HashMap<>();
        directionIntegerMap.put(direction, id);
        return directionIntegerMap;
    }


}
