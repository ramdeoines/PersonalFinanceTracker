package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.util.DateUtils;
import PersonalFinanceTracker.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Service class to handle exporting transactions to CSV files.
 */
public class ExportService {

    // === Export Methods ===

    /**
     * Exports a list of transactions to a CSV file.
     * 
     * @param transactions The list of transactions to export.
     * @param fileName The target filename for the CSV output.
     */
    public static void exportToCSV(List<Transaction> transactions, String fileName) {
        File file = new File(fileName);

        // Validate that the target file has a .csv extension
        if (!FileUtils.isValidCsvFile(file)) {
            System.out.println("❌ Error: Export file must have a .csv extension.");
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            // Write CSV header
            writer.append("ID,Amount,Category,Description,Date\n");

            // Write data rows
            for (Transaction t : transactions) {
                writer.append(String.format("%d,%.2f,%s,%s,%s\n",
                        t.getId(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getDescription().replace(",", ""),  // Sanitize to avoid breaking CSV format
                        DateUtils.formatDate(t.getDate())      // Consistent date formatting
                ));
            }

            System.out.println("✅ Exported transactions to " + fileName);

        } catch (IOException e) {
            System.out.println("❌ Failed to export transactions: " + e.getMessage());
        }
    }
}
