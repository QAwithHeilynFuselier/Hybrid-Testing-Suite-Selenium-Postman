 
# Hybrid Testing Suite: Selenium & Postman

This repository contains an automated testing framework designed for scalability and maintainability. It covers both UI automation with Selenium and API testing with Postman.

##  Live Test Reports
The project is integrated with a CI/CD pipeline that generates and hosts an Allure Report automatically.

**[View Live Allure Report](https://qawithheilynfuselier.github.io/Hybrid-Testing-Suite-Selenium-Postman/)**

---

 Technical Implementation

UI & Backend Automation
Architecture: Implemented the Page Object Model (POM) to separate test logic from UI elements, ensuring a clean "Separation of Concerns" [cite: 2026-01-28, 2026-02-04].

Design Pattern: Utilized Page Factory for efficient element initialization, which improves script maintainability and reduces boilerplate code [cite: 2026-01-28, 2026-02-04].

Database Validation (New): Integrated SQL queries via JDBC to perform backend data integrity checks. This ensures that information displayed on the UI is consistent with the database records [cite: 2026-02-04].

Language/Framework: Developed using Java 17 and Selenium WebDriver [cite: 2026-02-04].

 CI/CD Pipeline
* **GitHub Actions**: Automated the entire execution flow on every push [cite: 2026-02-04].
* **Reporting**: Integrated **Allure Reports** with history tracking to monitor test stability over time [cite: 2026-02-04].
* **Environment**: Tests run in **Headless mode** within an Ubuntu-based runner [cite: 2026-02-04].

