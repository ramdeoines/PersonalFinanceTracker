package PersonalFinanceTracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date formatting and parsing operations.
 */
public class DateUtils {

    // === Constants ===

    /** Default date formatter for consistent yyyy-MM-dd pattern across the app. */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // === Formatting Methods ===

    /**
     * Formats a LocalDate into a yyyy-MM-dd string.
     *
     * @param date The LocalDate to format.
     * @return A formatted date string, or empty string if input is null.
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(formatter) : "";
    }

    // === Parsing Methods ===

    /**
     * Parses a yyyy-MM-dd formatted string into a LocalDate.
     *
     * @param dateString The date string to parse.
     * @return A LocalDate object, or null if parsing fails.
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    // === Today's Date Helpers ===

    /**
     * Gets today's date formatted as a yyyy-MM-dd string.
     *
     * @return Formatted date string for today.
     */
    public static String getTodayDateString() {
        return LocalDate.now().format(formatter);
    }
}
