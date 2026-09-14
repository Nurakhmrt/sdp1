# Assignment 1: Builder Pattern — Cloud Deployment

This repository contains the implementation of Assignment 1 for the Software Design Patterns course at Astana IT University[cite: 1].

## Project Overview
* **Domain:** Cloud Deployment[cite: 1]
* **Language:** Java 17+ (Maven)[cite: 1]
* **Pattern:** Builder Pattern with Fluent API & Director[cite: 1]

## Project Structure
```text
assignment-1-builder/
├── src/
│   ├── main/java/org/example/  # Core logic (CloudDeployment, Builder, Director)
│   └── test/java/org/example/  # JUnit 5 Automated Tests
├── docs/
│   └── builder-uml.png         # UML Class Diagram
├── report.md                   # Detailed Assignment Report
└── README.md
```[cite: 1]

## How to Run

### Run Application
To run the main execution flow:
```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"

Run Tests
To execute all 10 automated JUnit 5 tests:
mvn test