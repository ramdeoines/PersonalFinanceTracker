package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to simulate future recurring transactions.
 */
public class RecurrenceSimulator {

    // === Simulation Methods ===

    /**
     * Generates a list of future transactions based on the recurrence pattern.
     *
     * @param original The original transaction to simulate from.
     * @param numberOfOccurrences How many future transactions to generate.
     * @return A list of future Transaction instances.
     */
    public static List<Transaction> simulateFutureOccurrences(Transaction original, int numberOfOccurrences) {
        List<Transaction> futureTransactions = new ArrayList<>();

        LocalDate currentDate = original.getDate();

        for (int i = 1; i <= numberOfOccurrences; i++) {
            // Determine next date based on recurrence type
            LocalDate nextDate = switch (original.getRecurrence().toLowerCase()) {
                case "daily" -> currentDate.plusDays(i);
                case "weekly" -> currentDate.plusWeeks(i);
                case "monthly" -> currentDate.plusMonths(i);
                default -> null;
            };

            if (nextDate == null) continue; // Skip if recurrence type is invalid

            Transaction future = new Transaction(
                    original.getAmount(),
                    original.getCategory(),
                    original.getDescription() + " (Recurring Copy)",
                    nextDate,
                    true,
                    original.getRecurrence()
            );

            futureTransactions.add(future);
        }

        return futureTransactions;
    }
}
