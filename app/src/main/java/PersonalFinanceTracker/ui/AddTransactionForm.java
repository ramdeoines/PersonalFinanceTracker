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
 * UI window to add a new transaction entry.
 */
public class AddTransactionForm {

    // === Display Method ===

    /**
     * Opens the "Add Transaction" form window.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Add Transaction");
        window.initModality(Modality.APPLICATION_MODAL);

        // === Form Inputs ===
        TextField amountInput = new TextField();
        TextField categoryInput = new TextField();
        TextField descriptionInput = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());

        CheckBox recurringCheck = new CheckBox("Is Recurring?");
        ComboBox<String> recurrenceDropdown = new ComboBox<>();
        recurrenceDropdown.getItems().addAll("daily", "weekly", "monthly");
        recurrenceDropdown.setDisable(true);

        // Enable recurrence dropdown only if checkbox is selected
        recurringCheck.setOnAction(e -> recurrenceDropdown.setDisable(!recurringCheck.isSelected()));

        // === Submit Button ===
        Button submitBtn = new Button("Submit");

        submitBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountInput.getText().trim());
                String category = categoryInput.getText().trim();
                String description = descriptionInput.getText().trim();
                LocalDate date = datePicker.getValue();
                boolean isRecurring = recurringCheck.isSelected();
                String recurrence = isRecurring ? recurrenceDropdown.getValue() : null;

                Transaction newTx = new Transaction(amount, category, description, date, isRecurring, recurrence);
                TransactionService.addTransaction(newTx);

                window.close();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        // === Layout Setup ===
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Amount:"), 0, 0);
        grid.add(amountInput, 1, 0);

        grid.add(new Label("Category:"), 0, 1);
        grid.add(categoryInput, 1, 1);

        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionInput, 1, 2);

        grid.add(new Label("Date:"), 0, 3);
        grid.add(datePicker, 1, 3);

        grid.add(recurringCheck, 0, 4);
        grid.add(recurrenceDropdown, 1, 4);

        grid.add(submitBtn, 1, 5);

        // === Scene and Window ===
        Scene scene = new Scene(grid, 350, 300);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }
}
