package PersonalFinanceTracker;

import PersonalFinanceTracker.db.DatabaseManager;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.ExportService;
import PersonalFinanceTracker.service.TransactionService;
import PersonalFinanceTracker.service.ExportService;
import PersonalFinanceTracker.service.SummaryService;
import java.util.Map;
import PersonalFinanceTracker.service.RecurrenceSimulator;
import PersonalFinanceTracker.ui.ConsoleUI;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleUI.launch();
        System.out.println("Welcome to the Personal Finance Tracker!");
        DatabaseManager.initializeDatabase();

        // Add a new transaction
        Transaction recurring = new Transaction(15.50, "Transport", "Metro card top-up", LocalDate.now(), true, "monthly");
        TransactionService.addTransaction(recurring);

        // Show all transactions
        List<Transaction> all = TransactionService.getAllTransactions();
        System.out.println("📄 All Transactions:");
        for (Transaction t : all) {
            System.out.println("ID " + t.getId() + ": " + t);
        }

        // Delete the first transaction (if there are any)
        if (!all.isEmpty()) {
            int idToDelete = all.get(0).getId();
            TransactionService.deleteTransactionById(idToDelete);
        }

        // Show updated list
        List<Transaction> updated = TransactionService.getAllTransactions();
        System.out.println("📄 Transactions After Deletion:");
        for (Transaction t : updated) {
            System.out.println("ID " + t.getId() + ": " + t);
        }

        // Export to CSV
        ExportService.exportToCSV(updated, "transactions.csv");

        if (!updated.isEmpty()) {
            Transaction toUpdate = updated.get(0); // get the most recent
            toUpdate = new Transaction(
                toUpdate.getId(),
                99.99,
                "Updated Category",
                "Edited transaction",
                toUpdate.getDate()
            );
        
            TransactionService.updateTransaction(toUpdate);
        }
        
        // Final display
        List<Transaction> finalList = TransactionService.getAllTransactions();
        System.out.println("📄 Transactions After Update:");
        for (Transaction t : finalList) {
            System.out.println("ID " + t.getId() + ": " + t);
        }

        System.out.println("📊 Summary:");
        System.out.printf("Total spent today: $%.2f%n", SummaryService.getTotalSpentToday(finalList));
        System.out.printf("Total spent this month: $%.2f%n", SummaryService.getTotalSpentThisMonth(finalList));
        System.out.printf("Total spent overall: $%.2f%n", SummaryService.getTotalSpent(finalList));

        System.out.println("📂 Spending by Category:");
        Map<String, Double> categoryTotals = SummaryService.getSpendingByCategory(finalList);
        for (String category : categoryTotals.keySet()) {
            System.out.printf("- %s: $%.2f%n", category, categoryTotals.get(category));
        }

        System.out.println("🔍 Category Filter: 'Updated Category'");
        List<Transaction> filtered = SummaryService.getTransactionsByCategory(finalList, "Updated Category");

        for (Transaction t : filtered) {
            System.out.println("ID " + t.getId() + ": " + t);
        }

        System.out.printf("💰 Total for 'Updated Category': $%.2f%n",
                SummaryService.getTotalForCategory(finalList, "Updated Category"));

    // Preview next 3 future instances of the first recurring transaction
    for (Transaction t : finalList) {
        if (t.isRecurring()) {
            System.out.println("🔮 Future instances of: " + t);
            List<Transaction> upcoming = RecurrenceSimulator.simulateFutureOccurrences(t, 3);
            for (Transaction ft : upcoming) {
                System.out.println("→ " + ft);
            }
            break; // just one for now
        }
    }

    }
}
