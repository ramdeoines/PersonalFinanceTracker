package PersonalFinanceTracker.service;

import PersonalFinanceTracker.db.DatabaseManager;
import PersonalFinanceTracker.model.Transaction;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SummaryServiceTest {

    @BeforeAll
    static void setup() {
        DatabaseManager.useTestDatabase();
        DatabaseManager.initializeDatabase();
    }

    @BeforeEach
    void clearDB() {
        try (var conn = DatabaseManager.connect(); var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM transactions;");
        } catch (Exception e) {
            System.out.println("❌ Error clearing DB before test: " + e.getMessage());
        }
    }

    @Test
    void testTotalSpentToday() {
        TransactionService.addTransaction(new Transaction(25.0, "Food", "Lunch", LocalDate.now(), false, null));
        TransactionService.addTransaction(new Transaction(30.0, "Transport", "Bus fare", LocalDate.now(), false, null));

        List<Transaction> transactions = TransactionService.getAllTransactions();
        double totalToday = SummaryService.getTotalSpentToday(transactions);

        assertEquals(55.0, totalToday, 0.01, "Total spent today should be 55.0");
    }

    @Test
    void testSpendingByCategory() {
        TransactionService.addTransaction(new Transaction(10.0, "Food", "Snack", LocalDate.now(), false, null));
        TransactionService.addTransaction(new Transaction(20.0, "Transport", "Taxi", LocalDate.now(), false, null));
        TransactionService.addTransaction(new Transaction(5.0, "Food", "Juice", LocalDate.now(), false, null));

        List<Transaction> transactions = TransactionService.getAllTransactions();
        Map<String, Double> categoryTotals = SummaryService.getSpendingByCategory(transactions);

        assertEquals(15.0, categoryTotals.get("Food"), 0.01);
        assertEquals(20.0, categoryTotals.get("Transport"), 0.01);
    }
}
