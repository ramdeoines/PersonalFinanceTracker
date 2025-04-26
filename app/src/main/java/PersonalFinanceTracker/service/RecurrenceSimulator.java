package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurrenceSimulator {

    public static List<Transaction> simulateFutureOccurrences(Transaction original, int numberOfOccurrences) {
        List<Transaction> futureTransactions = new ArrayList<>();

        LocalDate currentDate = original.getDate();

        for (int i = 1; i <= numberOfOccurrences; i++) {
            LocalDate nextDate = switch (original.getRecurrence().toLowerCase()) {
                case "daily" -> currentDate.plusDays(i);
                case "weekly" -> currentDate.plusWeeks(i);
                case "monthly" -> currentDate.plusMonths(i);
                default -> null;
            };

            if (nextDate == null) continue;

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
