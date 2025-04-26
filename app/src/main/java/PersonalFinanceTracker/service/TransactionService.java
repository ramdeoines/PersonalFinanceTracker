package PersonalFinanceTracker.service;

import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.db.DatabaseManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public static void addTransaction(Transaction transaction) {
        String sql = """
            INSERT INTO transactions(amount, category, description, date, is_recurring, recurrence)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
    
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setDouble(1, transaction.getAmount());
            pstmt.setString(2, transaction.getCategory());
            pstmt.setString(3, transaction.getDescription());
            pstmt.setString(4, transaction.getDate().toString());
            pstmt.setInt(5, transaction.isRecurring() ? 1 : 0);
            pstmt.setString(6, transaction.getRecurrence());
    
            pstmt.executeUpdate();
            System.out.println("✅ Transaction added: " + transaction);
    
        } catch (SQLException e) {
            System.out.println("❌ Error adding transaction: " + e.getMessage());
        }
    }

    public static List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions ORDER BY date DESC";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                String description = rs.getString("description");
                LocalDate date = LocalDate.parse(rs.getString("date"));

                boolean isRecurring = rs.getInt("is_recurring") == 1;
                String recurrence = rs.getString("recurrence");

                transactions.add(new Transaction(id, amount, category, description, date, isRecurring, recurrence));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error retrieving transactions: " + e.getMessage());
        }

        return transactions;
    }

    public static boolean updateTransaction(Transaction updatedTransaction) {
        String sql = """
            UPDATE transactions
            SET amount = ?, category = ?, description = ?, date = ?
            WHERE id = ?
        """;
    
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setDouble(1, updatedTransaction.getAmount());
            pstmt.setString(2, updatedTransaction.getCategory());
            pstmt.setString(3, updatedTransaction.getDescription());
            pstmt.setString(4, updatedTransaction.getDate().toString());
            pstmt.setInt(5, updatedTransaction.getId());
    
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("✅ Transaction with ID " + updatedTransaction.getId() + " updated.");
                return true;
            } else {
                System.out.println("⚠️ No transaction found with ID " + updatedTransaction.getId());
                return false;
            }
    
        } catch (SQLException e) {
            System.out.println("❌ Error updating transaction: " + e.getMessage());
            return false;
        }
    }
    

    public static boolean deleteTransactionById(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
    
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("✅ Transaction with ID " + id + " deleted.");
                return true;
            } else {
                System.out.println("⚠️ No transaction found with ID " + id);
                return false;
            }
    
        } catch (SQLException e) {
            System.out.println("❌ Error deleting transaction: " + e.getMessage());
            return false;
        }
    }
}
