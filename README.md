# Ecommerce QA Automation Project

[![API Tests](https://github.com/QAwithHeilynFuselier/ecommerce-qa-testing-project/actions/workflows/api-tests.yml/badge.svg)](https://github.com/QAwithHeilynFuselier/ecommerce-qa-testing-project/actions)

##  Overview
This project demonstrates a comprehensive **T-Shaped QA** approach for an E-commerce platform. It focuses on ensuring system reliability through automated API testing and CI/CD integration, with a roadmap toward Full-Stack Test Automation (UI + API).

## Tech Stack
* **API Testing:** [Postman](https://www.postman.com/) & [Newman](https://www.npmjs.com/package/newman)
* **CI/CD:** [GitHub Actions](https://github.com/features/actions)
* **Environment:** Node.js
* **Planned:** Java, Selenium WebDriver, and Gradle for UI Automation.

##  Automated Workflows
### 1. API Testing (Postman + Newman)
I have developed an automated test suite for the Amazon API (Edge Cases) located in the `api-tests/` directory.
* **Coverage:** Validation of Status Codes, Response Time, JSON Schema, and Error Handling (Edge Cases).
* **Automation:** Integrated with GitHub Actions to trigger automatically on every `push` to the main branch.

### 2. Continuous Integration (CI)
The project uses a custom GitHub Actions workflow (`.github/workflows/api-tests.yml`) that:
1.  Spins up a virtual Ubuntu environment.
2.  Installs Node.js and Newman.
3.  Executes the Postman collection.
4.  Reports results directly in the **Actions** tab.

##  Project Structure
* `api-tests/`: Postman Collections and Environment variables.
* `.github/workflows/`: CI/CD Pipeline configuration files.
* `ui-tests/`: (In Progress) Selenium WebDriver scripts.

##  How to Run Locally
1. Clone the repository:
   ```bash
   git clone [https://github.com/QAwithHeilynFuselier/ecommerce-qa-testing-project.git](https://github.com/QAwithHeilynFuselier/ecommerce-qa-testing-project.git)
