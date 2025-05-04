# 💸 Personal Finance Tracker

A Java-based desktop application to track personal expenses, built with JavaFX and SQLite. This project demonstrates basic software engineering skills including structured UI development, data persistence, user interaction, and reporting.

---

## ✨ Features

- ✅ Add, view, edit, and delete transactions  
- 🔍 Search transactions by category  
- 📈 View summaries: Today, This Month, Overall  
- ♻️ Recurring transaction support  
- 📤 Export transactions to CSV  
- ⚙️ Settings: Change export filename, toggle theme  
- 📊 Dashboard for visual insight  
- 🌙 Dark theme UI (via CSS)

---

## 🛠️ Tech Stack

- **Java 21**  
- **JavaFX** (for GUI)  
- **SQLite** (via `sqlite-jdbc`)  
- **JUnit 5** (for tests)  
- **Gradle** (build and dependency tool)  
- **MVC-style project structure**

---

## 🚀 How to Run

```bash
git clone https://github.com/your-username/finance-tracker.git
cd finance-tracker
./gradlew run

ℹ️ Make sure you have Java 21 installed and your JAVA_HOME is correctly set.


📁 Notes
All data is stored locally using an embedded SQLite database.

JavaFX UI uses a custom dark theme defined in dark-theme.css.

Ideal for basic CRUD operations and demonstrating familiarity with desktop Java development.