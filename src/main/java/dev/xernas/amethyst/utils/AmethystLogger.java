package dev.xernas.amethyst.utils;

import dev.xernas.amethyst.io.protocol.IPacket;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AmethystLogger {

    private static SimpleDateFormat format = new SimpleDateFormat("[dd/MM/YYYY][HH:mm:ss]");

    private static final String PREFIX = ConsoleColors.YELLOW + "[AMETHYST]" + ConsoleColors.RESET + ConsoleColors.WHITE + format.format(new Date());

    public static void info(String s) {
        System.out.println(PREFIX + ConsoleColors.BLUE + " " + s + ConsoleColors.RESET);
    }

    public static void warn(String s) {
        System.out.println(PREFIX + ConsoleColors.RED + " " + s + ConsoleColors.RESET);
    }

    public static void displayPacket(IPacket packet, boolean send) {
        if (send) {
            info("---OUT---");
            info(ConsoleColors.PURPLE + packet.getClass().getSimpleName());
            info("---------");
        }
        else {
            info("---IN---");
            info(ConsoleColors.PURPLE + packet.getClass().getSimpleName());
            info("--------");
        }
    }

}
