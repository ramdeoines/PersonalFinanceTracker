package PersonalFinanceTracker.model;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private double amount;
    private String category;
    private String description;
    private LocalDate date;
    private boolean isRecurring;
    private String recurrence; // daily, weekly, monthly

    public Transaction(int id, double amount, String category, String description, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public Transaction(int id, double amount, String category, String description, LocalDate date,
                        boolean isRecurring, String recurrence) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.isRecurring = isRecurring;
        this.recurrence = recurrence;
    }

    public Transaction(double amount, String category, String description, LocalDate date,
                        boolean isRecurring, String recurrence) {
        this(-1, amount, category, description, date, isRecurring, recurrence);
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isRecurring() {
        return isRecurring;
    }
    
    public String getRecurrence() {
        return recurrence;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    // toString
    @Override
    public String toString() {
        String recurringStr = isRecurring ? " [Recurring: " + recurrence + "]" : "";
        return "[" + date + "] " + category + " - $" + amount + " (" + description + ")" + recurringStr;
    }
}
