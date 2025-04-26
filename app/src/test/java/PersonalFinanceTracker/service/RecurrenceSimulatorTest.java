package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecurrenceSimulatorTest {

    @Test
    void testMonthlyRecurrence() {
        Transaction original = new Transaction(50.0, "Subscription", "Netflix", LocalDate.of(2025, 1, 1), true, "monthly");

        List<Transaction> future = RecurrenceSimulator.simulateFutureOccurrences(original, 3);

        assertEquals(3, future.size());
        assertEquals(LocalDate.of(2025, 2, 1), future.get(0).getDate());
        assertEquals(LocalDate.of(2025, 3, 1), future.get(1).getDate());
        assertEquals(LocalDate.of(2025, 4, 1), future.get(2).getDate());
    }

    @Test
    void testWeeklyRecurrence() {
        Transaction original = new Transaction(10.0, "Transport", "Weekly Metro", LocalDate.of(2025, 4, 7), true, "weekly");

        List<Transaction> future = RecurrenceSimulator.simulateFutureOccurrences(original, 2);

        assertEquals(2, future.size());
        assertEquals(LocalDate.of(2025, 4, 14), future.get(0).getDate());
        assertEquals(LocalDate.of(2025, 4, 21), future.get(1).getDate());
    }

    @Test
    void testUnsupportedRecurrence() {
        Transaction original = new Transaction(100.0, "Mystery", "Test", LocalDate.of(2025, 4, 1), true, "yearly");

        List<Transaction> future = RecurrenceSimulator.simulateFutureOccurrences(original, 2);

        assertTrue(future.isEmpty(), "Unsupported recurrence should return no future transactions");
    }
}
