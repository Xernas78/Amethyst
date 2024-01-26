package dev.xernas.amethyst.network.protocol;

import dev.xernas.amethyst.network.NetworkManager;
import dev.xernas.amethyst.network.protocol.configuration.FinishConfigurationPacket;
import dev.xernas.amethyst.network.protocol.handshake.HandshakePacket;
import dev.xernas.amethyst.network.protocol.login.LoginAknowledgedPacket;
import dev.xernas.amethyst.network.protocol.login.LoginStartPacket;
import dev.xernas.amethyst.network.protocol.login.LoginSuccessPacket;
import dev.xernas.amethyst.network.protocol.play.JoinGamePacket;
import dev.xernas.amethyst.network.protocol.status.PingPongPacket;
import dev.xernas.amethyst.network.protocol.status.StatusPacket;
import dev.xernas.amethyst.network.util.Bound;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PacketRegistry {

    private static final Map<Bound, Map<Integer, Map<Integer, Class<? extends IPacket>>>> packetMap = new HashMap<>();

    public static void registerPackets() {
        register(Bound.SERVER, 0, 0x00, HandshakePacket.class);
        register(Bound.BOTH, 1, 0x00, StatusPacket.class);
        register(Bound.BOTH, 1, 0x01, PingPongPacket.class);
        register(Bound.SERVER, 2, 0x00, LoginStartPacket.class);
        register(Bound.CLIENT, 2, 0x02, LoginSuccessPacket.class);
        register(Bound.SERVER, 2, 0x03, LoginAknowledgedPacket.class);
        register(Bound.BOTH,3, 0x02, FinishConfigurationPacket.class);
        register(Bound.CLIENT, 4, 0x29, JoinGamePacket.class);
    }

    public static void register(Bound bound, Integer state, Integer id, Class<? extends IPacket> packet) {
        //Bound
        Map<Integer, Map<Integer, Class<? extends IPacket>>> packets = packetMap.get(bound);
        if (packets == null) {
            packets = new HashMap<>();
        }
        //State
        Map<Integer, Class<? extends IPacket>> statePackets = packets.get(state);
        if (statePackets == null) {
            statePackets = new HashMap<>();
        }
        //Packet
        statePackets.put(id, packet);
        packets.put(state, statePackets);
        packetMap.put(bound, packets);
    }

    public static Integer getId(Bound bound, Class<? extends IPacket> iPacket, NetworkManager networkManager) {
        AtomicReference<Integer> id = new AtomicReference<>(null);

        if (packetMap.get(bound) == null || packetMap.get(bound).get(networkManager.getState()) == null) {
            bound = Bound.BOTH;
        }
        if (packetMap.get(bound) == null || packetMap.get(bound).get(networkManager.getState()) == null) {
            return null;
        }

        Map<Integer, Class<? extends IPacket>> packets = packetMap.get(bound).get(networkManager.getState());
        packets.forEach((packetId, packet) -> {
            if (packet == iPacket) {
                id.set(packetId);
            }
        });

        return id.get();
    }

    public static IPacket getById(Bound bound, Integer id, NetworkManager networkManager) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (packetMap.get(bound) == null || packetMap.get(bound).get(networkManager.getState()) == null || packetMap.get(bound).get(networkManager.getState()).get(id) == null) {
            bound = Bound.BOTH;
        }
        if (packetMap.get(bound) != null) {
            if (packetMap.get(bound).get(networkManager.getState()) != null) {
                if (packetMap.get(bound).get(networkManager.getState()).get(id) != null) {
                    return packetMap.get(bound).get(networkManager.getState()).get(id).getDeclaredConstructor().newInstance();
                }
            }
        }
        return null;
    }

}
