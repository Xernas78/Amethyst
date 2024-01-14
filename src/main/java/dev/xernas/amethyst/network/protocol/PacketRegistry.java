package dev.xernas.amethyst.network.protocol;

import dev.xernas.amethyst.network.StateManager;
import dev.xernas.amethyst.network.protocol.handshake.HandshakePacket;
import dev.xernas.amethyst.network.protocol.login.LoginStartPacket;
import dev.xernas.amethyst.network.protocol.login.LoginSuccessPacket;
import dev.xernas.amethyst.network.protocol.status.PingPongPacket;
import dev.xernas.amethyst.network.protocol.status.StatusPacket;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketRegistry {

    private static final Map<Integer, Map<Integer, Class<? extends IPacket>>> packetMap = new HashMap<>();

    public static void registerPackets() {
        register(0, 0x00, HandshakePacket.class);
        register(1, 0x00, StatusPacket.class);
        register(1, 0x01, PingPongPacket.class);
        register(2, 0x00, LoginStartPacket.class);
        register(2, 0x02, LoginSuccessPacket.class);
    }

    public static void register(Integer state, Integer id, Class<? extends IPacket> packet) {
        Map<Integer, Class<? extends IPacket>> packets = packetMap.get(state);
        if (packets == null) {
            packets = new HashMap<>();
        }
        packets.put(id, packet);
        packetMap.put(state, packets);
    }

    public static Integer getId(Class<? extends IPacket> iPacket) {
        AtomicInteger id = new AtomicInteger(-1);
        packetMap.get(StateManager.getState()).forEach((integer, packet) -> {
            if (packet.equals(iPacket)) {
                id.set(integer);
            }
        });
        return id.get();
    }

    public static IPacket getById(Integer id) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (packetMap.get(StateManager.getState()) != null) {
            if (packetMap.get(StateManager.getState()).get(id) != null) {
                return packetMap.get(StateManager.getState()).get(id).getDeclaredConstructor().newInstance();
            }
        }
        System.out.println("Couldn't find packet id " + id);
        return null;
    }

}
