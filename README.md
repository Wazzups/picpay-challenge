# PicPay Simplified – Back-end Challenge

This project is a simplified implementation of a payment platform inspired by PicPay, built with Spring Boot and Java.

## Technologies
- Java 21 (Spring Boot)
- H2
- PostgresSQL
- Docker

## Implementation
The transfer has transaction management with `@Transactional` for rollback if needed and notification event-driven to not block transaction and trigger after commit. 
Exception handler and validation implemented


### Flow
- User (common users and merchants) with wallets
- Money transfers between users (wallets)
  - Balance validation
  - Merchants can only receive, not send
  - External authorization service call
  - Transactional (rollback on failure)
  - Transaction persistence
  - Payee notification after commit
  - 

## API Endpoints

## Postman
#### [Picpay postman](Picpay.postman_collection.json)

### POST `/transfer` Endpoint to make a transfer between users

**Request**

```json
{
  "value": 100.0,
  "payerId": 1,
  "payeeId": 2
}
```

**Response 200 OK**

```json
{
  "transactionId": "<UUID>",
  "amount": 100.0,
  "payerId": 1,
  "payeeId": 2,
  "timestamp": "2025-06-12T14:30:00"
}
```

**Exceptions handlers**

| HTTP Status             | Scenario                                        |
| ----------------------- | ----------------------------------------------- |
| 400 Bad Request         | Insufficient balance or invalid request payload |
| 401 Unauthorized        | External authorization denied                   |
| 403 Forbidden           | Merchant attempting to send                     |
| 404 Not Found           | Payer or payee not found                        |
| 503 Service Unavailable | Authorization service failure                   |

## Getting Started

### Prerequisites

- Java 21
- Maven
- Docker

### Running Locally (H2 In-Memory)

1. Build and start the application:
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```
3. The API will be available at `http://localhost:8080`.
4. (Optional) Access the H2 console:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:picpaydb;`
   - User Name: `sa` / Password:&#x20;

### Running with Docker Compose (PostgreSQL)

1. Optionally adjust credentials in `docker-compose.yml`.
2. Bring up the services:
   ```bash
   docker-compose up --build
   ```
3. The API will be available at `http://localhost:8080`.
4. To connect to PostgreSQL from your host:
   - Host: `localhost`
   - Port: `5433` (or as defined in `docker-compose.yml`)
   - Database: `picpay`
   - User: `picpayuser`
   - Password: `secret`


    
## Future Improvements

- Retry and queue for pending notifications
- Populate BD with data when init application.

---
