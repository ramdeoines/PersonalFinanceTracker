package PersonalFinanceTracker.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * SettingsWindow displays application settings like export preferences,
 * theme toggle, and app information.
 */
public class SettingsWindow {

    private static String defaultExportFilename = "transactions.csv"; // Default CSV filename
    private static boolean darkThemeEnabled = true; // Default to dark mode

    /**
     * Opens the settings window.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Settings");

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-alignment: top-left;");

        // --- Export Filename Section ---
        Label exportLabel = new Label("Default Export Filename:");
        TextField exportFilenameField = new TextField(defaultExportFilename);
        Button saveExportBtn = new Button("Save Filename");

        saveExportBtn.setOnAction(e -> {
            String input = exportFilenameField.getText().trim();
            if (!input.isEmpty()) {
                defaultExportFilename = input;
                showAlert("Default export filename updated.");
            } else {
                showAlert("Filename cannot be empty.");
            }
        });

        // --- Theme Toggle Section ---
        Label themeLabel = new Label("Theme:");
        ToggleButton themeToggle = new ToggleButton("Dark Mode");
        themeToggle.setSelected(darkThemeEnabled);

        themeToggle.setOnAction(e -> {
            darkThemeEnabled = themeToggle.isSelected();
            themeToggle.setText(darkThemeEnabled ? "Dark Mode" : "Light Mode");
            showAlert("Theme setting updated. Restart app to fully apply.");
        });

        // --- About Section ---
        Label aboutLabel = new Label("About:");
        Label versionLabel = new Label("Version: 1.0");
        Label authorLabel = new Label("Author: Rameses Deo Ines");

        // Add all components to the VBox layout
        vbox.getChildren().addAll(
            exportLabel, exportFilenameField, saveExportBtn,
            new Separator(),
            themeLabel, themeToggle,
            new Separator(),
            aboutLabel, versionLabel, authorLabel
        );

        // Scene setup with dark theme styling
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(SettingsWindow.class.getResource("/style/dark-theme.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }

    /**
     * Displays a simple informational alert.
     */
    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    // --- Public Getters for Settings ---
    
    public static String getDefaultExportFilename() {
        return defaultExportFilename;
    }

    public static boolean isDarkThemeEnabled() {
        return darkThemeEnabled;
    }
}
