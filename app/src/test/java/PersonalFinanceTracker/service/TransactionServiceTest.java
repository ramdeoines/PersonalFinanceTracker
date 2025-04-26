package PersonalFinanceTracker.service;

import PersonalFinanceTracker.db.DatabaseManager;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.TransactionService;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @BeforeAll
    static void setup() {
        DatabaseManager.useTestDatabase();
        DatabaseManager.initializeDatabase(); // Ensure table exists
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
    void testAddAndFetchTransaction() {
        Transaction t = new Transaction(50.0, "Test", "JUnit test add", LocalDate.now(), false, null);
        TransactionService.addTransaction(t);

        List<Transaction> transactions = TransactionService.getAllTransactions();
        assertFalse(transactions.isEmpty(), "Transaction list should not be empty");

        boolean found = transactions.stream()
                .anyMatch(tx -> tx.getDescription().equals("JUnit test add") && tx.getAmount() == 50.0);

        assertTrue(found, "Added transaction should be in the list");
    }

    @Test
    void testDeleteTransaction() {
        Transaction t = new Transaction(20.0, "Test", "JUnit delete test", LocalDate.now(), false, null);
        TransactionService.addTransaction(t);

        List<Transaction> transactions = TransactionService.getAllTransactions();
        int idToDelete = transactions.get(transactions.size() - 1).getId();

        boolean deleted = TransactionService.deleteTransactionById(idToDelete);
        assertTrue(deleted, "Transaction should be deleted");

        List<Transaction> updatedList = TransactionService.getAllTransactions();
        boolean stillExists = updatedList.stream().anyMatch(tx -> tx.getId() == idToDelete);

        assertFalse(stillExists, "Deleted transaction should not exist anymore");
    }
}
