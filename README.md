 
# Hybrid Testing Suite: Selenium & Postman

This repository contains an automated testing framework designed for scalability and maintainability. It covers both UI automation with Selenium and API testing with Postman.

##  Live Test Reports
The project is integrated with a CI/CD pipeline that generates and hosts an Allure Report automatically.

**[View Live Allure Report](https://qawithheilynfuselier.github.io/Hybrid-Testing-Suite-Selenium-Postman/)**

---

 ## Technical Implementation

#UI & Backend Automation
Architecture: Implemented the Page Object Model (POM) to separate test logic from UI elements, ensuring a clean Separation of Concerns 

Design Pattern: Utilized Page Factory for efficient element initialization, which improves script maintainability and reduces boilerplate code.

Database Validation (New): Integrated SQL queries via JDBC to perform backend data integrity checks. This ensures that information displayed on the UI is consistent with the database records 

Language/Framework: Developed using Java 17 and Selenium WebDriver

 CI/CD Pipeline
* **GitHub Actions**: Automated the entire execution flow on every push
* **Reporting**: Integrated **Allure Reports** with history tracking to monitor test stability over time 
* **Environment**: Tests run in **Headless mode** within an Ubuntu-based runner 


# Advanced AI & Performance Modules
AI Reliability & LLM Evaluation: Integrated a Python-based evaluation engine to detect LLM Hallucinations and biases, using a "Model-as-a-Judge" approach to compare model outputs against ground-truth data.

Performance & Scalability Engineering: Developed comprehensive load and stress test suites using Apache JMeter (.jmx) to identify authentication bottlenecks and ensure system stability under high-traffic scenarios.

 Multi-Language Hybrid Architecture: Orchestrated a cross-platform environment managing Java (JDK) for UI/API and Python (Virtual Environments) for AI validation within a single IDE structure
