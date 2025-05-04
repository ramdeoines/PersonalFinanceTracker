package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.Main;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.RecurrenceSimulator;
import PersonalFinanceTracker.service.TransactionService;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * RecurringPreviewWindow creates a JavaFX window that previews
 * the next few occurrences of each recurring transaction.
 */
public class RecurringPreviewWindow {

    /**
     * Displays a new window showing a preview of future recurring transactions.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Preview Recurring Transactions");

        // Retrieve all transactions from the service layer
        List<Transaction> allTransactions = TransactionService.getAllTransactions();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-alignment: top-left;");

        boolean hasRecurring = false;

        // Loop through each transaction to find and simulate recurring ones
        for (Transaction t : allTransactions) {
            if (t.isRecurring()) {
                hasRecurring = true;

                // Display the original recurring transaction
                Label original = new Label("🔁 " + t);
                vbox.getChildren().add(original);

                // Simulate and display the next 3 future occurrences
                List<Transaction> future = RecurrenceSimulator.simulateFutureOccurrences(t, 3);
                for (Transaction f : future) {
                    Label futureLabel = new Label("→ " + f);
                    vbox.getChildren().add(futureLabel);
                }

                // Add a spacer between entries for visual separation
                vbox.getChildren().add(new Label(""));
            }
        }

        // Handle case where no recurring transactions exist
        if (!hasRecurring) {
            vbox.getChildren().add(new Label("📭 No recurring transactions found."));
        }

        // Setup the scene and apply styling
        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());

        window.setScene(scene);
        window.show();
    }
}
