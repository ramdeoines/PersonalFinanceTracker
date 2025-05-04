package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.Main;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.SummaryService;
import PersonalFinanceTracker.service.TransactionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * SearchTransactionsWindow displays a window that allows users
 * to filter and view transactions by their category.
 */
public class SearchTransactionsWindow {

    /**
     * Launches the search interface for filtering transactions by category.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Search Transactions by Category");

        // Input field for category name
        TextField searchInput = new TextField();
        searchInput.setPromptText("Enter category...");

        // Button to trigger search
        Button searchBtn = new Button("Search");

        // Table to display transactions
        TableView<Transaction> table = new TableView<>();
        table.setPlaceholder(new Label("No results."));

        // Define table columns
        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("category"));

        TableColumn<Transaction, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("description"));

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

        // Custom cell rendering for date to avoid null text
        dateCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String dateStr, boolean empty) {
                super.updateItem(dateStr, empty);
                setText(empty || dateStr == null ? null : dateStr);
            }
        });

        // Add all columns to the table
        table.getColumns().addAll(idCol, amountCol, categoryCol, descriptionCol, dateCol);

        // Handle search button action
        searchBtn.setOnAction(e -> {
            String category = searchInput.getText().trim();
            if (!category.isEmpty()) {
                List<Transaction> all = TransactionService.getAllTransactions();
                List<Transaction> filtered = SummaryService.getTransactionsByCategory(all, category);
                ObservableList<Transaction> data = FXCollections.observableArrayList(filtered);
                table.setItems(data);
            }
        });

        // Layout container with spacing and padding
        VBox vbox = new VBox(10, searchInput, searchBtn, table);
        vbox.setPadding(new Insets(15));

        // Scene setup with dark theme styling
        Scene scene = new Scene(vbox, 700, 500);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());

        window.setScene(scene);
        window.show();
    }
}
