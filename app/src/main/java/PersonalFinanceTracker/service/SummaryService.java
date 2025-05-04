package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class to calculate summaries and analytics for transactions.
 */
public class SummaryService {

    // === Overall Totals ===

    /**
     * Calculates the total amount spent across all transactions.
     *
     * @param transactions The list of transactions.
     * @return The total amount spent.
     */
    public static double getTotalSpent(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // === Daily and Monthly Totals ===

    /**
     * Calculates the total amount spent today.
     *
     * @param transactions The list of transactions.
     * @return The total amount spent today.
     */
    public static double getTotalSpentToday(List<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getDate().equals(today))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the total amount spent in the current month.
     *
     * @param transactions The list of transactions.
     * @return The total amount spent this month.
     */
    public static double getTotalSpentThisMonth(List<Transaction> transactions) {
        YearMonth thisMonth = YearMonth.now();
        return transactions.stream()
                .filter(t -> YearMonth.from(t.getDate()).equals(thisMonth))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // === Category Summaries ===

    /**
     * Groups total spending amounts by category.
     *
     * @param transactions The list of transactions.
     * @return A map with category names as keys and total amounts as values.
     */
    public static Map<String, Double> getSpendingByCategory(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction t : transactions) {
            categoryTotals.put(
                    t.getCategory(),
                    categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount()
            );
        }

        return categoryTotals;
    }

    /**
     * Retrieves all transactions for a specific category.
     *
     * @param transactions The list of transactions.
     * @param category The category to filter by.
     * @return A list of transactions that match the category.
     */
    public static List<Transaction> getTransactionsByCategory(List<Transaction> transactions, String category) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /**
     * Calculates the total amount spent for a specific category.
     *
     * @param transactions The list of transactions.
     * @param category The category to filter by.
     * @return The total amount spent for that category.
     */
    public static double getTotalForCategory(List<Transaction> transactions, String category) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
