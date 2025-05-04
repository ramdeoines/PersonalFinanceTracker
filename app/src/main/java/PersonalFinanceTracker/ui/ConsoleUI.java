package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.db.DatabaseManager;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ConsoleUI provides a command-line interface for interacting with the Personal Finance Tracker.
 * It allows users to add, view, delete, export, and summarize financial transactions.
 */
public class ConsoleUI {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Entry point for the console-based application.
     * Displays a menu and handles user input in a loop.
     */
    public static void launch() {
        DatabaseManager.initializeDatabase();

        while (true) {
            System.out.println("\n💸 Personal Finance Tracker");
            System.out.println("1. Add Transaction");
            System.out.println("2. View All Transactions");
            System.out.println("3. Delete Transaction by ID");
            System.out.println("4. Export to CSV");
            System.out.println("5. View Summary");
            System.out.println("6. Preview Recurring Transactions");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            switch (scanner.nextLine()) {
                case "1" -> addTransaction();
                case "2" -> viewAll();
                case "3" -> deleteById();
                case "4" -> export();
                case "5" -> viewSummary();
                case "6" -> previewRecurring();
                case "0" -> {
                    System.out.println("👋 Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
     * Prompts the user to enter transaction details and adds it to the system.
     */
    private static void addTransaction() {
        try {
            System.out.print("Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Category: ");
            String category = scanner.nextLine();

            System.out.print("Description: ");
            String desc = scanner.nextLine();

            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Is recurring? (yes/no): ");
            boolean isRecurring = scanner.nextLine().equalsIgnoreCase("yes");

            String recurrence = null;
            if (isRecurring) {
                System.out.print("Recurrence (daily/weekly/monthly): ");
                recurrence = scanner.nextLine();
            }

            Transaction t = new Transaction(amount, category, desc, date, isRecurring, recurrence);
            TransactionService.addTransaction(t);
            System.out.println("✅ Transaction added.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Displays all transactions in the system.
     */
    private static void viewAll() {
        List<Transaction> transactions = TransactionService.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("📭 No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println("ID " + t.getId() + ": " + t);
            }
        }
    }

    /**
     * Deletes a transaction by ID based on user input.
     */
    private static void deleteById() {
        try {
            System.out.print("Enter transaction ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean deleted = TransactionService.deleteTransactionById(id);
            if (deleted) {
                System.out.println("🗑️ Transaction deleted.");
            } else {
                System.out.println("❌ No transaction found with that ID.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Exports all transactions to a CSV file.
     */
    private static void export() {
        ExportService.exportToCSV(TransactionService.getAllTransactions(), "transactions.csv");
        System.out.println("📁 Transactions exported to transactions.csv");
    }

    /**
     * Displays a financial summary including totals and category-wise spending.
     */
    private static void viewSummary() {
        List<Transaction> list = TransactionService.getAllTransactions();

        System.out.printf("📅 Total Today: $%.2f%n", SummaryService.getTotalSpentToday(list));
        System.out.printf("📅 Total This Month: $%.2f%n", SummaryService.getTotalSpentThisMonth(list));
        System.out.printf("💰 Total Overall: $%.2f%n", SummaryService.getTotalSpent(list));

        System.out.println("📊 Spending by Category:");
        for (Map.Entry<String, Double> entry : SummaryService.getSpendingByCategory(list).entrySet()) {
            System.out.printf("- %s: $%.2f%n", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Previews future occurrences of recurring transactions.
     */
    private static void previewRecurring() {
        List<Transaction> all = TransactionService.getAllTransactions();

        for (Transaction t : all) {
            if (t.isRecurring()) {
                System.out.println("🔄 " + t);
                var preview = RecurrenceSimulator.simulateFutureOccurrences(t, 3);
                for (Transaction ft : preview) {
                    System.out.println("→ " + ft);
                }
            }
        }
    }
}
