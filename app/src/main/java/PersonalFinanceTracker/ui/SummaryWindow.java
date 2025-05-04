package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.Main;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.SummaryService;
import PersonalFinanceTracker.service.TransactionService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

/**
 * SummaryWindow displays a summary of the user's spending,
 * including totals for today, this month, overall, and per category.
 */
public class SummaryWindow {

    /**
     * Launches the summary window showing key spending metrics.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Summary");

        // Retrieve all transaction data
        List<Transaction> transactions = TransactionService.getAllTransactions();

        // --- Compute Summary Totals ---
        double totalToday = SummaryService.getTotalSpentToday(transactions);
        double totalMonth = SummaryService.getTotalSpentThisMonth(transactions);
        double totalOverall = SummaryService.getTotalSpent(transactions);
        Map<String, Double> categoryTotals = SummaryService.getSpendingByCategory(transactions);

        // --- Build Labels for Totals ---
        Label todayLabel = new Label(String.format("Total Spent Today: $%.2f", totalToday));
        Label monthLabel = new Label(String.format("Total Spent This Month: $%.2f", totalMonth));
        Label overallLabel = new Label(String.format("Total Spent Overall: $%.2f", totalOverall));

        VBox vbox = new VBox(10, todayLabel, monthLabel, overallLabel);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-alignment: center;");

        // --- Add Category Breakdown if present ---
        if (!categoryTotals.isEmpty()) {
            vbox.getChildren().add(new Label("Spending by Category:"));
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                Label categoryLabel = new Label(String.format("- %s: $%.2f", entry.getKey(), entry.getValue()));
                vbox.getChildren().add(categoryLabel);
            }
        }

        // --- Setup Scene and Show Window ---
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
}
