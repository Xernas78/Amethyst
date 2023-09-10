package dev.xernas.amethyst.io.protocol.packets.login;

import dev.xernas.amethyst.chat.TextChatComponent;
import dev.xernas.amethyst.io.AmethystServer;
import dev.xernas.amethyst.io.protocol.IPacket;
import dev.xernas.amethyst.io.util.MCByteBuf;
import dev.xernas.amethyst.io.util.State;
import dev.xernas.amethyst.io.util.StateManager;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EncryptionPacket implements IPacket {

    private KeyPair key;
    private byte[] verifyToken;

    public EncryptionPacket() {

    }

    public EncryptionPacket(KeyPair key, byte[] verifyToken) {
        this.key = key;
        this.verifyToken = verifyToken;
    }
    @Override
    public Map<String, Object> read(MCByteBuf byteBuf) {
        Map<String, Object> map = new HashMap<>();
        map.put("sharedSecret", byteBuf.readByteArray());
        map.put("verifyToken", byteBuf.readByteArray());
        return map;
    }

    @Override
    public void write(MCByteBuf byteBuf) {
        byteBuf.writeUTF("", 20);
        byteBuf.writeVarInt(key.getPublic().getEncoded().length);
        byteBuf.getByteBuf().writeBytes(key.getPublic().getEncoded());
        byteBuf.writeVarInt(verifyToken.length);
        byteBuf.getByteBuf().writeBytes(verifyToken);
    }

    @Override
    public void handle(MCByteBuf byteBuf, StateManager stateManager, ChannelHandlerContext ctx) {
        Map<String, Object> map = read(byteBuf);
        byte[] decryptedVerifyToken;
        try {
            decryptedVerifyToken = AmethystServer.getGenerator().decrypt(AmethystServer.getGenerator().getVerifyToken());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.close();
            return;
        }
        if (Arrays.equals(decryptedVerifyToken, AmethystServer.getGenerator().getVerifyToken())) {
            ctx.writeAndFlush(new LoginSuccessPacket(AmethystServer.getGameProfile()));
            stateManager.changeState(State.PLAY);
        }
        else {
            AmethystServer.getLogger().warning("Encryption Failed");
            ctx.writeAndFlush(new LoginDisconnectPacket(new TextChatComponent("Encryption Failed").setColor("red")));
            ctx.close();
        }
    }
}
