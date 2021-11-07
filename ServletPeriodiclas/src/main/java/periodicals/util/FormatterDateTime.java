package periodicals.util;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatterDateTime extends SimpleTagSupport {
    private FormatterDateTime() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
