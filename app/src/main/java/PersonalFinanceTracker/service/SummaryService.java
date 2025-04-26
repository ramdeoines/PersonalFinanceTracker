package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryService {

    public static double getTotalSpent(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static double getTotalSpentToday(List<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        return transactions.stream()
                .filter(t -> t.getDate().equals(today))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static double getTotalSpentThisMonth(List<Transaction> transactions) {
        YearMonth thisMonth = YearMonth.now();
        return transactions.stream()
                .filter(t -> YearMonth.from(t.getDate()).equals(thisMonth))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

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

    public static List<Transaction> getTransactionsByCategory(List<Transaction> transactions, String category) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public static double getTotalForCategory(List<Transaction> transactions, String category) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }    
}
