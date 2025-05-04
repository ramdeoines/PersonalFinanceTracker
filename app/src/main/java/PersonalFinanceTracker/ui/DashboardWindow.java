package PersonalFinanceTracker.ui;

import PersonalFinanceTracker.model.Transaction;
import PersonalFinanceTracker.service.SummaryService;
import PersonalFinanceTracker.service.TransactionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * DashboardWindow creates a new window displaying a financial overview:
 * - Summary labels for today, this month, and overall spending
 * - A pie chart of spending by category
 * - A bar chart of spending by month
 */
public class DashboardWindow {

    /**
     * Opens the dashboard window with charts and summary statistics.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Dashboard");

        // Fetch all transactions
        List<Transaction> transactions = TransactionService.getAllTransactions();

        // 1. Spending Summary Labels
        double totalToday = SummaryService.getTotalSpentToday(transactions);
        double totalMonth = SummaryService.getTotalSpentThisMonth(transactions);
        double totalOverall = SummaryService.getTotalSpent(transactions);

        Label todayLabel = new Label(String.format("Today: $%.2f", totalToday));
        Label monthLabel = new Label(String.format("This Month: $%.2f", totalMonth));
        Label overallLabel = new Label(String.format("Total Overall: $%.2f", totalOverall));

        // 2. Pie Chart: Spending by Category
        PieChart pieChart = createCategoryPieChart(transactions);

        // 3. Bar Chart: Monthly Spending
        BarChart<String, Number> barChart = createMonthlyBarChart(transactions);

        // Layout and Scene Setup
        VBox vbox = new VBox(20, todayLabel, monthLabel, overallLabel, pieChart, barChart);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-alignment: top-center;");

        Scene scene = new Scene(vbox, 800, 800);
        scene.getStylesheets().add(DashboardWindow.class.getResource("/style/dark-theme.css").toExternalForm());

        window.setScene(scene);
        window.show();
    }

    /**
     * Creates a PieChart that visualizes spending by category.
     *
     * @param transactions List of transactions to analyze.
     * @return A configured PieChart object.
     */
    private static PieChart createCategoryPieChart(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = SummaryService.getSpendingByCategory(transactions);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            pieData.add(new PieChart.Data(
                entry.getKey() + String.format(" ($%.2f)", entry.getValue()),
                entry.getValue()
            ));
        }

        PieChart pieChart = new PieChart(pieData);
        pieChart.setTitle("Spending by Category");
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(javafx.geometry.Side.BOTTOM); // Aesthetics: move legend below chart

        return pieChart;
    }

    /**
     * Creates a BarChart showing total spending grouped by month.
     *
     * @param transactions List of transactions to analyze.
     * @return A configured BarChart object.
     */
    private static BarChart<String, Number> createMonthlyBarChart(List<Transaction> transactions) {
        // Aggregate totals by month using TreeMap for natural month ordering
        Map<Month, Double> monthlyTotals = new TreeMap<>();
        for (Transaction t : transactions) {
            Month month = t.getDate().getMonth();
            monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + t.getAmount());
        }

        // Setup X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Amount ($)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Monthly Spending");
        barChart.setLegendVisible(false);
        barChart.setCategoryGap(10);
        barChart.setBarGap(3);

        // Populate the bar chart with data for each month
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Month month : Month.values()) {
            double amount = monthlyTotals.getOrDefault(month, 0.0);
            series.getData().add(new XYChart.Data<>(month.name().substring(0, 3), amount)); // Use 3-letter month names
        }

        barChart.getData().add(series);

        return barChart;
    }
}
