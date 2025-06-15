# enterprisetransaction

# 💰 Enterprise Transaction System (Spring Boot + Hibernate)

This project is a **banking transaction system** built using **Spring Boot**, **Hibernate**, and **Aspect-Oriented Programming (AOP)**. It demonstrates how to manage transactional integrity across operations such as **account creation**, **deposit**, **withdrawal**, and **fund transfer**, with proper isolation levels and audit logging.

---

## 📦 Features

- ✅ User and Account Creation  
- ✅ Deposit, Withdraw, and Transfer Funds  
- ✅ @Transactional with Isolation & Propagation Levels  
- ✅ AOP-based Transaction Logging  
- ✅ HQL Queries and Manual Entity Mapping  
- ✅ Global Exception Handling  

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Hibernate Mapping
- HQL
- Spring AOP
- Maven
- H2 Database

---

## 🔁 Transaction Management

Each service method uses `@Transactional` with appropriate **propagation** and **isolation** levels:

| Operation      | Propagation         | Isolation          |
|----------------|---------------------|--------------------|
| Create User    | REQUIRED            | SERIALIZABLE       |
| Deposit        | REQUIRED + AOP Log  | READ_COMMITTED     |
| Withdraw       | REQUIRED + AOP Log  | READ_COMMITTED     |
| Transfer       | REQUIRED + AOP Log  | REPEATABLE_READ    |
| Get Balance    | SUPPORTS (Read-only)| DEFAULT            |


Account APIs

| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| POST   | `/api/v1/account/create`          | Create user and account         |
| POST   | `/api/v1/account/deposit`         | Deposit funds into an account   |
| POST   | `/api/v1/account/withdraw`        | Withdraw funds from an account  |
| POST   | `/api/v1/account/transfer`        | Transfer funds to another account |
| GET    | `/api/v1/account/balance/{id}`    | Get account balance              |

### 🔄 Sample Request Payloads

#### Create User + Account
```json
POST /api/v1/account/create
{
  "name": "john",
  "email": "john@example.com",
  "accountType": "SAVINGS",
  "balance": 10000.0
}

Deposit Funds
POST /api/v1/account/deposit
{
  "toAccountId": 1,
  "amount": 5000.0
}

Transfer Funds
POST /api/v1/account/transfer
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 1000.0
}

📄 Transaction APIs
Method	Endpoint	Description
GET	/api/v1/transaction/history/{id}	Get all transactions for account
GET	/api/v1/transaction/summary	Get all transaction summaries


How to Run the Project Locally
🔧 Prerequisites
Java 17+
Maven 3.x

🧪 Steps
Clone the repository
git clone https://github.com/your-username/enterprise-banking.git
cd enterprise-banking

Build the project using Maven
mvn clean install

Run the Spring Boot application
mvn spring-boot:run

Swagger UI : http://localhost:8080/swagger-ui/index.html 
