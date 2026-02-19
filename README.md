
AI Evaluation Framework: LLM Reliability (Java Edition)
This feature branch introduces an advanced evaluation layer designed to audit and validate Generative AI outputs. The focus shifts from traditional functional UI testing to Model Reliability, Safety, and Truthfulness using a native Java stack.

Key Features
Java-Native Hallucination Detection: Implementation of a "Model-as-a-Judge" architecture using Java service classes. It compares LLM outputs against ground-truth reference data to identify fabricated information without leaving the JVM.

Adversarial & Bias Testing Suites: Automated TestNG/JUnit suites that leverage Data Providers to inject manipulative prompts (Jailbreaking) and evaluate model fairness across diverse datasets.

Unified SDK Orchestration: Eliminated Python dependency. The project now leverages a single JDK environment (managed via File > Project Structure > SDKs) to handle both the Web Infrastructure (Selenium/Playwright) and the AI Evaluation logic.

Programmatic AI Auditing: Direct integration with OpenAI/Gemini Java SDKs to programmatically score the accuracy, toxicity, and relevance of AI-generated responses.

Technical Implementation
Language: Java 17+

Build Tool: Maven / Gradle (Centralized dependency management in pom.xml).

Testing Engine: TestNG with Page Factory for seamless UI interaction.

Environment: Pure Java (No .venv or Python interpreters required).

Core Logic: Uses LLM-based Semantic Comparison and heuristic checks to determine if a response is a "Hallucination" or a "Verified Fact."
