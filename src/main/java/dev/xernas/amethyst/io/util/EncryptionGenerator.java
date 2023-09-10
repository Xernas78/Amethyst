package dev.xernas.amethyst.io.util;

import dev.xernas.amethyst.io.AmethystServer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

public class EncryptionGenerator {

    private KeyPair keyPair;
    private final byte[] verifyToken;

    public EncryptionGenerator() {
        try {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            AmethystServer.getLogger().warning(e.getMessage());
        }
        verifyToken = new byte[] {
                (byte) ThreadLocalRandom.current().nextInt(128),
                (byte) ThreadLocalRandom.current().nextInt(128),
                (byte) ThreadLocalRandom.current().nextInt(128),
                (byte) ThreadLocalRandom.current().nextInt(128)
        };
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public byte[] decrypt(final byte[] input) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        final Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return cipher.doFinal(input);
    }

}
