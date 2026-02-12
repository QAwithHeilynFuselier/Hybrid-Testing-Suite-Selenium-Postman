 
# Hybrid Testing Suite: Selenium & Postman

This repository contains an automated testing framework designed for scalability and maintainability. It covers both UI automation with Selenium and API testing with Postman.

##  Live Test Reports
The project is integrated with a CI/CD pipeline that generates and hosts an Allure Report automatically.

**[View Live Allure Report](https://qawithheilynfuselier.github.io/Hybrid-Testing-Suite-Selenium-Postman/)**

---

## Technical Implementation

### UI Automation
* **Architecture**: Implemented **Page Object Model (POM)** to separate test logic from UI elements [cite: 2026-01-28, 2026-02-04].
* **Design Pattern**: Utilized **Page Factory** for efficient element initialization and to reduce boilerplate code [cite: 2026-01-28, 2026-02-04].
* **Language/Framework**: Developed using **Java 17** and **Selenium WebDriver** [cite: 2026-02-04].

### CI/CD Pipeline
* **GitHub Actions**: Automated the entire execution flow on every push [cite: 2026-02-04].
* **Reporting**: Integrated **Allure Reports** with history tracking to monitor test stability over time [cite: 2026-02-04].
* **Environment**: Tests run in **Headless mode** within an Ubuntu-based runner [cite: 2026-02-04].

---

##  How to Run Locally
1. Clone the repository.
2. Ensure you have **JDK 17** installed.
3. Run the following command in the terminal:
   ```bash
   ./gradlew test
