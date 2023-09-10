package dev.xernas.amethyst.logging;

import java.util.logging.*;

public class AmethystConsole extends StreamHandler {

    private final ConsoleHandler stderrHandler = new ConsoleHandler();

    public AmethystConsole(Formatter formatter) {
        super(System.out, formatter);
        stderrHandler.setFormatter(formatter);
    }

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() <= Level.INFO.intValue()) {
            super.publish(record);
            super.flush();
        } else {
            stderrHandler.publish(record);
            stderrHandler.flush();
        }
    }
}
