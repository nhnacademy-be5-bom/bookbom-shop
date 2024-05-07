package shop.bookbom.shop.common;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    private DateFormatter() {
    }

    public static LocalDate parseLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }

    public static String formatLocalDate(LocalDate date) {
        return date.toString();
    }
}
