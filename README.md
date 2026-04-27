# Payment Reconciliation Engine
Financial system developed in Spring Boot to automate the matching process between bank transactions and internal orders.

## Tech Stack
- Java 17
- Spring Boot 3.x
- PostgreSQL
- Spring Data JPA / Hibernate
- Maven

# Roadmap and Progress
***Phase 1: Infrastructure and Data Architecture (Completed)***
- Setup of PostgreSQL database and Spring Boot environment.
- Implementation of core entities: Order and BankTransaction.
- Usage of BigDecimal for financial precision and LocalDateTime for audit trails.
- Established Many-to-One relationship between transactions and orders.


***Phase 2: CONTROL Module – Validation (In Progress)***
- Development of FinancialValidator to prevent zero or negative amount entries.
- Business rules for verifying order statuses before processing payments.
- Global exception handling for data ingestion.


***Phase 3: MATCHING Module – Business Logic***
- Regex-based pattern matching for identifying order IDs in transaction titles.
- Idempotency mechanisms to ensure no transaction is processed twice.


***Phase 4: REPORTING Module – Analytics***
- Custom JPQL queries for tracking unmatched cash and pending reconciliations.
- Automated daily revenue summaries.


## Setup Instructions
- Ensure PostgreSQL server is active.
- Create a local database named: reconciliation_db.
- Configure database credentials in src/main/resources/application.properties.
- Run the application using an IDE or mvn spring-boot:run.
