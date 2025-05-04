package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.Main;
import PersonalFinanceTracker.service.TransactionService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * UI dialog that allows the user to delete a transaction by entering its ID.
 */
public class DeleteTransactionForm {

    // === Entry Point ===

    /**
     * Displays a modal window where the user can input a transaction ID to delete.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Delete Transaction");
        window.initModality(Modality.APPLICATION_MODAL);

        // === UI Setup ===
        Label label = new Label("Enter Transaction ID to delete:");
        TextField idInput = new TextField();
        Button deleteBtn = new Button("Delete");

        // === Button Action Logic ===
        deleteBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText());

                boolean success = TransactionService.deleteTransactionById(id);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Transaction deleted successfully.");
                    window.close();
                } else {
                    showAlert(Alert.AlertType.WARNING, "Transaction with given ID not found.");
                }

            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid input: " + ex.getMessage());
            }
        });

        // === Layout ===
        VBox vbox = new VBox(10, label, idInput, deleteBtn);
        vbox.setPadding(new Insets(15));
        vbox.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add(Main.class.getResource("/style/dark-theme.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }

    // === Helper ===

    /**
     * Utility method to show an alert with a given type and message.
     */
    private static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
}
