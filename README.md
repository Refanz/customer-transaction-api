# Customer Transaction & Reporting API

This project implements a backend REST API using Spring Boot to manage customer transactions and generate various
financial reports. It is designed to be used by company staff to record transactions on behalf of customers and provides
a separate interface for customers to view their own data.

## Table of Contents

1. [Features](#features)

2. [API Endpoints Overview](#api-endpoints-overview)

    * [Authentication](#authentication)

    * [User Management](#user-management)

    * [Customer Management](#customer-management)

    * [Product Management](#product-management)

    * [Transaction Management](#transaction-management)

    * [Reporting](#reporting)

3. [Authentication & Authorization](#authentication--authorization)

4. [Technologies Used](#technologies-used)

5. [Setup & Running the Project](#setup--running-the-project)

6. [License](#license)

## Features

The API provides comprehensive functionalities across several domains:

* **User Management:**

    * CRUD operations for internal staff users.

    * Role-based access control:

        * **Admin:** Full access to all API operations.

        * **User:** Can modify their own profile, create new transactions, and generate reports.

    * User login supported by either username or email.

* **Customer Management:**

    * CRUD operations for customer data, primarily for manual registration by staff.

    * Customer attributes: `name`, `birthdate`, `birthplace`, `created_by`, `created_at`, `updated_at`, `updated_by`.

    * Allows for customers with the same name, birthdate, and birthplace.

    * Customers can log in and update their own profiles.

    * Customers can download their personal transaction reports, limited to their own transactions.

* **Product Management:**

    * CRUD operations for product data.

    * Product attributes: `name`, `price` (tax-excluded).

    * Flexible tax handling: Each product can have multiple associated taxes or no taxes.

* **Transaction Management:**

    * CRUD operations for transaction records.

    * Transaction attributes: `customer`, `net_amount_paid`, `total_amount_paid`, `total_tax_paid`, `transaction_time`,
      `payment_status` (paid, not paid, cancelled), `payment_method`.

    * Advanced querying capabilities for transactions:

        * Filter by date range.

        * Filter by customer (using customer name).

        * Filter by payment status (single, multiple, or all statuses).

        * Filter by payment method (single, multiple, or all methods).

        * Sort by transaction time (oldest or newest first).

        * Filter by the staff user who created the transaction.

* **Transaction Reporting:**

    * Generate reports based on transaction data:

        * Total amount of money spent by a specific customer within a given date range.

        * Total amount of money spent by a specific customer throughout their entire transaction history.

        * Total amount of money spent per tax type.

        * Total amount of money spent per product.

## API Endpoints Overview

This section provides a high-level conceptual overview of the REST API endpoints. Detailed request/response bodies and
specific query parameters will be documented within the API implementation.

### Authentication

* `POST /api/auth/login`: Authenticate a staff user (username/email and password).

* `POST /api/auth/customer/login`: Authenticate a customer.

### User Management

*(Accessible by Admin; `GET /api/users/{id}` and `PUT /api/users/{id}` accessible by User for their own profile)*

* `GET /api/users`: Retrieve a list of all users.

* `GET /api/users/{id}`: Retrieve details of a specific user.

* `POST /api/users`: Create a new user.

* `PUT /api/users/{id}`: Update an existing user's details.

* `DELETE /api/users/{id}`: Delete a user.

* `PUT /api/users/{id}/roles`: Update roles for a specific user.

### Customer Management

*(Accessible by Staff for CRUD; `GET /api/customers/{id}` and `PUT /api/customers/{id}` accessible by Customer for their
own profile)*

* `GET /api/customers`: Retrieve a list of all customers.

* `GET /api/customers/{id}`: Retrieve details of a specific customer.

* `POST /api/customers`: Create a new customer.

* `PUT /api/customers/{id}`: Update an existing customer's details.

* `DELETE /api/customers/{id}`: Delete a customer.

* `GET /api/customers/{id}/transactions/report`: Download transaction report for a specific customer (customer's own
  transactions).

### Product Management

*(Accessible by Staff)*

* `GET /api/products`: Retrieve a list of all products.

* `GET /api/products/{id}`: Retrieve details of a specific product.

* `POST /api/products`: Create a new product.

* `PUT /api/products/{id}`: Update an existing product's details.

* `DELETE /api/products/{id}`: Delete a product.

* `POST /api/products/{id}/taxes`: Add a tax to a product.

* `DELETE /api/products/{id}/taxes/{taxId}`: Remove a tax from a product.

### Transaction Management

*(Accessible by Staff)*

* `GET /api/transactions`: Retrieve a list of transactions with filtering and sorting options.

    * Query parameters for `startDate`, `endDate`, `customerName`, `paymentStatus` (comma-separated), `paymentMethod` (
      comma-separated), `sortBy` (`transactionTimeOldest`/`transactionTimeNewest`), `createdByStaffId`.

* `GET /api/transactions/{id}`: Retrieve details of a specific transaction.

* `POST /api/transactions`: Create a new transaction.

* `PUT /api/transactions/{id}`: Update an existing transaction's details.

* `DELETE /api/transactions/{id}`: Delete a transaction.

### Reporting

*(Accessible by Staff and Users)*

* `GET /api/reports/transactions/customer/{customerId}/total`: Get total amount spent by a customer.

    * Optional query parameters: `startDate`, `endDate`.

* `GET /api/reports/transactions/tax/total`: Get total amount spent per tax.

* `GET /api/reports/transactions/product/total`: Get total amount spent per product.

## Authentication & Authorization

The API uses a token-based authentication mechanism (e.g., JWT).

* **Users (Staff):**

    * Can log in with their username or email.

    * Assigned roles: `ADMIN` or `USER`.

    * `ADMIN` role has unrestricted access to all API endpoints.

    * `USER` role is restricted to modifying their own user profile, creating new transactions, and accessing reporting
      endpoints.

* **Customers:**

    * Can log in using their customer credentials.

    * Access is restricted to updating their own customer profile and downloading their own transaction reports.

## Technologies Used

* **Backend Framework:** Spring Boot

* **Language:** Kotlin

* **Build Tool:** Gradle

* **Database:** PostgreSQL

* **Security:** Spring Security

* **Data Access:** Spring Data JPA / Hibernate

* **API Documentation:** OpenAPI/Swagger UI

## Setup & Running the Project

1. **Prerequisites:**

    * Java Development Kit (JDK) 17 or newer.

    * Maven or Gradle (depending on build tool choice).

    * A running database instance (e.g., PostgreSQL).

2. **Clone the repository:**

```git
git clone https://github.com/Refanz/customer-transaction-api.git
cd customer-transaction-api
```

3. **Configure Database:**

* Update `src/main/resources/application.properties` (or `application.yml`) with your database connection details (URL,
  username, password).

4. **Build the project:**

Using Gradle:

```bash 
./gradlew build
```

5. **Run the application:**

Using Gradle:

```bash
./gradlew bootRun
```

6. **Access the API:**
   The API will typically run on `http://localhost:8080`.

## License

This project is licensed under the MIT License.
