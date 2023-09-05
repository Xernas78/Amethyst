package dev.xernas.glowstone.utils;

public class GlowstoneLogger {

    private final String PREFIX = ConsoleColors.YELLOW + "[GLOWSTONE]" + ConsoleColors.RESET;

    public void info(String s) {
        System.out.println(PREFIX + ConsoleColors.BLUE + " " + s + ConsoleColors.RESET);
    }

    public void warn(String s) {
        System.out.println(PREFIX + ConsoleColors.RED + " " + s + ConsoleColors.RESET);
    }

}
