package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.Main;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.TransactionService;
import PersonalFinanceTracker.util.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

/**
 * UI window that displays all transactions in a table format.
 */
public class ViewTransactionsWindow {

    // === Display Method ===

    /**
     * Opens the window to view all transactions in a table.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("View All Transactions");

        // === Table Setup ===
        TableView<Transaction> table = new TableView<>();

        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Transaction, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Transaction, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Custom cell renderer to format LocalDate values
        dateCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(DateUtils.formatDate(date));
                }
            }
        });

        TableColumn<Transaction, Boolean> recurringCol = new TableColumn<>("Recurring");
        recurringCol.setCellValueFactory(new PropertyValueFactory<>("recurring"));

        table.getColumns().addAll(
                idCol, amountCol, categoryCol, descriptionCol, dateCol, recurringCol
        );

        // === Data Binding ===
        List<Transaction> transactions = TransactionService.getAllTransactions();
        ObservableList<Transaction> data = FXCollections.observableArrayList(transactions);
        table.setItems(data);

        // === Layout and Window ===
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 700, 400);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());

        window.setScene(scene);
        window.show();
    }
}
