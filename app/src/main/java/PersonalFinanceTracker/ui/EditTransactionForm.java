package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.Main;
import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.TransactionService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * UI dialog that allows users to edit an existing transaction by ID.
 */
public class EditTransactionForm {

    /**
     * Opens the transaction editing modal window.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Edit Transaction");
        window.initModality(Modality.APPLICATION_MODAL);

        // === Form Fields ===
        TextField idInput = new TextField();
        TextField amountInput = new TextField();
        TextField categoryInput = new TextField();
        TextField descriptionInput = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        Label statusLabel = new Label("");

        Button loadBtn = new Button("Load Transaction");
        Button submitBtn = new Button("Submit Changes");

        // === Load Button Logic ===
        loadBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText());

                Transaction original = TransactionService.getAllTransactions()
                        .stream()
                        .filter(t -> t.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (original != null) {
                    amountInput.setText(String.valueOf(original.getAmount()));
                    categoryInput.setText(original.getCategory());
                    descriptionInput.setText(original.getDescription());
                    datePicker.setValue(original.getDate());
                    statusLabel.setText("Transaction loaded. Edit fields and submit.");
                } else {
                    statusLabel.setText("Transaction ID not found.");
                }

            } catch (Exception ex) {
                statusLabel.setText("Invalid ID input.");
            }
        });

        // === Submit Button Logic ===
        submitBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText());
                double amount = Double.parseDouble(amountInput.getText());
                String category = categoryInput.getText();
                String description = descriptionInput.getText();
                LocalDate date = datePicker.getValue();

                Transaction updatedTx = new Transaction(id, amount, category, description, date);

                boolean success = TransactionService.updateTransaction(updatedTx);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Transaction updated successfully.");
                    window.close();
                } else {
                    showAlert(Alert.AlertType.WARNING, "Failed to update transaction.");
                }

            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid input: " + ex.getMessage());
            }
        });

        // === Layout ===
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Transaction ID:"), 0, 0);
        grid.add(idInput, 1, 0);
        grid.add(loadBtn, 2, 0);

        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountInput, 1, 1);

        grid.add(new Label("Category:"), 0, 2);
        grid.add(categoryInput, 1, 2);

        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionInput, 1, 3);

        grid.add(new Label("Date:"), 0, 4);
        grid.add(datePicker, 1, 4);

        grid.add(submitBtn, 1, 5);
        grid.add(statusLabel, 1, 6, 2, 1);

        // === Scene Setup ===
        Scene scene = new Scene(grid, 500, 400);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }

    // === Helper Method ===

    /**
     * Shows an alert of the specified type with a given message.
     */
    private static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
