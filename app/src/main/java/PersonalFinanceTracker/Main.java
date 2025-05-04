package PersonalFinanceTracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// UI Windows
import PersonalFinanceTracker.ui.AddTransactionForm;
import PersonalFinanceTracker.ui.ViewTransactionsWindow;
import PersonalFinanceTracker.ui.DeleteTransactionForm;
import PersonalFinanceTracker.ui.EditTransactionForm;
import PersonalFinanceTracker.ui.SearchTransactionsWindow;
import PersonalFinanceTracker.ui.SummaryWindow;
import PersonalFinanceTracker.ui.RecurringPreviewWindow;
import PersonalFinanceTracker.ui.DashboardWindow;
import PersonalFinanceTracker.ui.SettingsWindow;

// Services
import PersonalFinanceTracker.service.TransactionService;
import PersonalFinanceTracker.service.ExportService;

/**
 * Main class for launching the Personal Finance Tracker application.
 */
public class Main extends Application {

    /**
     * Entry point for the JavaFX application.
     *
     * @param primaryStage the main window
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Personal Finance Tracker");

        // --- Create Action Buttons ---
        Button addButton = createButton("Add Transaction", AddTransactionForm::display);
        Button viewButton = createButton("View All Transactions", ViewTransactionsWindow::display);
        Button searchButton = createButton("Search Transactions", SearchTransactionsWindow::display);
        Button deleteButton = createButton("Delete Transaction", DeleteTransactionForm::display);
        Button editButton = createButton("Edit Transaction", EditTransactionForm::display);
        Button summaryButton = createButton("View Summary", SummaryWindow::display);
        Button previewButton = createButton("Preview Recurring", RecurringPreviewWindow::display);
        Button dashboardButton = createButton("Dashboard", DashboardWindow::display);
        Button settingsButton = createButton("Settings", SettingsWindow::display);

        // --- Export Button with Custom Logic ---
        Button exportButton = new Button("Export to CSV");
        exportButton.setOnAction(e -> {
            var transactions = TransactionService.getAllTransactions();
            ExportService.exportToCSV(transactions, SettingsWindow.getDefaultExportFilename());
            showAlert("Transactions exported to " + SettingsWindow.getDefaultExportFilename());
        });

        // --- Layout Setup ---
        VBox vbox = new VBox(10,
            dashboardButton,
            addButton, viewButton, searchButton,
            deleteButton, editButton, exportButton,
            summaryButton, previewButton,
            settingsButton
        );
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 400, 450);
        scene.getStylesheets().add(getClass().getResource("/style/dark-theme.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Utility method for creating a button with a label and action.
     *
     * @param label  the text to show on the button
     * @param action the action to execute on click
     * @return the created Button
     */
    private Button createButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(e -> action.run());
        return button;
    }

    /**
     * Displays a simple informational alert.
     *
     * @param message the message to show
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    /**
     * Main method — launches the JavaFX application.
     */
    public static void main(String[] args) {
        launch();
    }
}
