package dev.xernas.amethyst.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class AmethystFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        Date date = new Date(record.getMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("[yyyy-MM-dd/HH:mm:ss]");
        String formatted = dateFormat.format(date);
        return formatted + "[AMETHYST]" + "["+record.getLevel()+"]" + " : " + record.getMessage() + "\n";
    }
}
