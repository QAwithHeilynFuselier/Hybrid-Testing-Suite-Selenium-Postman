
Project Overview
This project demonstrates a professional Hybrid QA Automation approach for the Koel music streaming platform. It implements a scalable architecture using the Page Object Model (POM) and Page Factory patterns to ensure clean, maintainable, and readable UI test code, alongside a robust API testing suite integrated into a CI/CD pipeline.

🛠 Tech Stack
UI Automation: Java, Selenium WebDriver, TestNG

Design Pattern: Page Factory (Lazy Initialization & Object Repository)

API Testing: Postman & Newman

CI/CD: GitHub Actions & Jenkins

Build Tool: Gradle 8.5 (Optimized for fast builds and Allure integration)

Reporting: Allure Reports (with historical trends)

 
 Automated Workflows
1. UI Automation (Page Factory Model)
Developed a modular UI testing suite located in the src/ directory.

Coverage: User authentication (Edge Cases), Info Panel validation (Lyrics, Artist, Album), and Shuffle functionality.

Logic: Implemented intelligent validation for "Unknown" metadata and fallback messages (e.g., the "Bach" lyrics message).

Architecture: Leverage Page Factory for efficient element locating and reduced code duplication.

2. API Testing (Postman + Newman)
Located in the api-tests/ directory, validating backend stability.

Coverage: Status Codes, Response Time, JSON Schema validation, and Error Handling.

CI Integration: Automatically triggered on every push via GitHub Actions.
