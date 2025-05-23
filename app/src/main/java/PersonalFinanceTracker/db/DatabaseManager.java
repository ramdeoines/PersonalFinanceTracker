package PersonalFinanceTracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseManager handles connecting to and initializing the SQLite database
 * used for storing financial transactions.
 */
public class DatabaseManager {

    // Default database URL
    private static String DB_URL = "jdbc:sqlite:finance_tracker.db";

    /**
     * Switches the active database connection to a test database.
     */
    public static void useTestDatabase() {
        DB_URL = "jdbc:sqlite:test_finance_tracker.db";
    }

    /**
     * Establishes and returns a connection to the currently selected database.
     *
     * @return Connection to the database
     * @throws SQLException if connection fails
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Initializes the transactions table if it does not exist.
     * This method ensures the database schema is ready for use.
     */
    public static void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                amount REAL NOT NULL,
                category TEXT NOT NULL,
                description TEXT,
                date TEXT NOT NULL,
                is_recurring INTEGER DEFAULT 0,
                recurrence TEXT
            );
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("✅ Database and table initialized.");
        } catch (SQLException e) {
            System.out.println("❌ Error initializing database: " + e.getMessage());
        }
    }
}
