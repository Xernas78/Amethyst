package dev.xernas.glowstone.io.protocol;

import dev.xernas.glowstone.io.protocol.packets.handshake.HandshakePacket;
import dev.xernas.glowstone.io.protocol.packets.status.PingPongPacket;
import dev.xernas.glowstone.io.protocol.packets.status.StatusPacket;
import dev.xernas.glowstone.io.util.State;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PacketRegistry {

    private final static Map<Integer, IPacket> HANDSHAKE_MAP = new HashMap<>();
    private final static Map<Integer, IPacket> STATUS_MAP = new HashMap<>();
    private final static Map<Integer, IPacket> LOGIN_MAP = new HashMap<>();
    private final static Map<State, Map<Integer, IPacket>> PACKET_MAP = new HashMap<>();

    public static void setup() {
        registerPacket(State.HANDSHAKE, 0x00, new HandshakePacket());
        registerPacket(State.STATUS, 0x00, new StatusPacket());
        registerPacket(State.STATUS, 0x01, new PingPongPacket());
        PACKET_MAP.put(State.HANDSHAKE, HANDSHAKE_MAP);
        PACKET_MAP.put(State.STATUS, STATUS_MAP);
        PACKET_MAP.put(State.LOGIN, LOGIN_MAP);
    }

    private static void registerPacket(State state, Integer id, IPacket packet) {
        switch (state) {
            case HANDSHAKE -> HANDSHAKE_MAP.put(id, packet);
            case STATUS -> STATUS_MAP.put(id, packet);
            case LOGIN -> LOGIN_MAP.put(id, packet);
        }
    }

    public static IPacket getPacket(State state, Integer id) throws NullPointerException{
        Map<Integer, IPacket> stateMap = PACKET_MAP.get(state);
        return stateMap.get(id);
    }

    public static Integer getId(State state, IPacket packet) throws NullPointerException{
        Map<Integer, IPacket> stateMap = PACKET_MAP.get(state);
        for (int i = 0; i < stateMap.size(); i++) {
            if (stateMap.get(i).getClass().getSimpleName().equals(packet.getClass().getSimpleName())) {
                return i;
            }
        }
        return null;
    }


}
