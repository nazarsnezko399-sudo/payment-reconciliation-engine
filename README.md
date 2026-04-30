# Payment Reconciliation Engine
Financial system developed in Spring Boot to automate the matching process between bank transactions and internal orders.

## Tech Stack
- Java 17+ 
- Spring Boot 3.x
- PostgreSQL
- Spring Data JPA / Hibernate
- JUnit 5 & Mockito (Testing)
- Maven

# Roadmap and Progress

***Phase 1: Infrastructure and Data Architecture (Completed)***
- Setup of PostgreSQL database and Spring Boot environment.
- Implementation of core entities: Order and BankTransaction.
- Usage of BigDecimal for financial precision.
- Established Relationship between transactions and orders.

***Phase 2: CONTROL Module – Validation & Error Handling (Completed)***
- Development of FinancialValidator to prevent zero or negative amount entries.
- Business rules for verifying order statuses (Status-based validation).
- Global Exception Handling:** Custom JSON error responses for better API communication.
- REST API: Implementation of GET/POST endpoints for order management.

***Phase 3: MATCHING Module – Business Logic (In Progress)***
- Automated Testing: Unit tests with Mockito to ensure service reliability.
- Regex-based pattern matching for identifying order IDs in transaction titles.
- Idempotency mechanisms to ensure no transaction is processed twice.

***Phase 4: DEPLOYMENT & REPORTING (Upcoming)***
- Dockerization: Containerizing the app and database.
- Custom JPQL queries for tracking unmatched cash.

## API Usage (Quick Start)
- **Get Order Details:** `GET http://localhost:8080/api/reconciliation/order/{id}`
- **Process Payment:** `POST http://localhost:8080/api/reconciliation/process?orderId={id}`

## Setup Instructions
- Ensure PostgreSQL server is active.
- Create a local database named: `reconciliation_db`.
- Configure database credentials in `src/main/resources/application.properties`.
- Run the application using an IDE or `mvn spring-boot:run`.