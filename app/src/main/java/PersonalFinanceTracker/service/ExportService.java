package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportService {

    public static void exportToCSV(List<Transaction> transactions, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Header
            writer.append("ID,Amount,Category,Description,Date\n");

            // Data rows
            for (Transaction t : transactions) {
                writer.append(String.format("%d,%.2f,%s,%s,%s\n",
                        t.getId(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getDescription().replace(",", ""),  // Avoid comma breaking CSV
                        t.getDate().toString()
                ));
            }

            System.out.println("✅ Exported transactions to " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Failed to export transactions: " + e.getMessage());
        }
    }
}
