

AI Evaluation Framework (LLM Reliability)
This feature branch introduces an advanced evaluation layer designed to audit and validate Generative AI outputs. The focus is shifted from functional UI testing to Model Reliability and Safety.

Key Features
Hallucination Detection: Implementation of a "Model-as-a-Judge" script (Model_Hallucination_Check.py) that compares LLM outputs against ground-truth reference data to identify fabricated information.

Adversarial & Bias Testing: Automated suites to test model resilience against manipulative prompts and evaluate fairness across different datasets.

Hybrid SDK Orchestration: Leverages a custom environment setup where Java (JDK) manages the web infrastructure and Python (Virtual Environments) handles the AI evaluation logic.

Automated AI Auditing: Integration of OpenAI's API to programmatically score the accuracy and relevance of AI-generated responses.

Technical Implementation
Language: Python 3.x .

Environment Management: Utilizes .venv for dependency isolation and requirements tracking.

Core Logic: Uses heuristic and LLM-based comparisons to determine if a response is a "Hallucination" or "Verified Fact
