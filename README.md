 
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
* **Environment**: Tests run in **Headless mode** within an Ubuntu-based runner.


Performance & Scalability Engineering
Beyond functional automation, this framework includes dedicated performance testing suites to ensure system reliability under pressure:

***Load & Stress Testing: Developed and executed .jmx scripts using Apache JMeter to simulate high-user concurrency and identify system breaking points.

***Targeted Scenarios: Implemented specific tests for critical flows, such as Koel_Login_LoadTest.jmx and Koel_Stress_Test.jmx, to validate authentication stability .

***Bottleneck Identification: Analyzed key performance indicators (KPIs) including Throughput, Latency, and Error Rates to diagnose server-side degradation.

****Infrastructure Analysis: Evaluated how concurrent traffic impacts backend resources, ensuring the database and server maintain integrity during peak loads

