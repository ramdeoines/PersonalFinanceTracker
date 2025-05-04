package PersonalFinanceTracker.util;

import java.io.File;

/**
 * Utility class for file-related operations such as extension checking.
 */
public class FileUtils {

    // === File Extension Methods ===

    /**
     * Gets the file extension of a given file.
     *
     * @param file The file to inspect.
     * @return The file extension in lowercase (without the dot), or empty string if none.
     */
    public static String getFileExtension(File file) {
        if (file == null || !file.isFile()) return "";
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot > 0 && lastDot < name.length() - 1) {
            return name.substring(lastDot + 1).toLowerCase();
        }
        return "";
    }

    // === File Validation Methods ===

    /**
     * Checks if a file has a .csv extension.
     *
     * @param file The file to validate.
     * @return True if the file is a CSV file, false otherwise.
     */
    public static boolean isValidCsvFile(File file) {
        return getFileExtension(file).equals("csv");
    }
}
