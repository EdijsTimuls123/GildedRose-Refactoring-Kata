# 🧪 Gilded Rose Refactoring Kata – Java Version

This is a refactored Java solution for the classic Gilded Rose Kata. The goal is to apply clean code practices, separation of concerns, and extensibility while preserving original behavior.

---

## 🛠️ Technologies Used

- Java 17+
- Maven (for build and dependency management)
- JUnit 5 (for testing)
- TextTest-compatible CLI simulation

---

## 📋 Prerequisites

- JDK 17 or higher
- Maven installed (`mvn -v` to verify)
- Git (if cloning the repository)

---

## 🚀 Build and Run Instructions

### 🧱 Step 1: Clone the Repository

```bash
git clone https://github.com/<your-username>/GildedRose-Refactoring-Kata.git
cd GildedRose-Refactoring-Kata/Java
```

### 🧹 Step 2: Clean and Build
```bash
mvn clean install
```

### ▶️ Step 3: Run the Simulation (TextTest Fixture)
You can run the simulation from the command line, optionally specifying the number of days to simulate. If no days argument is provided, it defaults to 10 days.

```bash
Run from command line:
java -cp target/test-classes com.gildedrose.TexttestFixture (default 10 days)
java -cp target/test-classes com.gildedrose.TexttestFixture 15 (passing days)

Run sh script:
chmod +x run.sh
./run.sh (default 10 days)
./run.sh 15 (passing days)
```
