package pro.sky.telegrambot.util;

import java.util.regex.Pattern;

public class BotUtil {

    public static final Pattern VALID_DATE_PATTERN =
            Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
}
