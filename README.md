# E-commerce REST API

## Overview

This repository contains a RESTful API for an E-commerce platform. The project
is designed following the Hexagonal Architecture (Ports and Adapters) pattern,
ensuring a clear separation of concerns between the core domain logic,
application use cases, and external infrastructure (databases and web delivery).

The system handles product management, secure user authentication via JSON Web
Tokens (JWT), and order processing with automated stock validation and deduction
mechanisms.

## Architecture

The project structure is divided into three primary layers:

- **Domain:** Contains pure business logic, entities (`User`, `Product`, `Order`),
  and domain services. It has zero dependencies on external frameworks.
- **Application:** Defines the inbound and outbound ports, orchestrating use cases
  (`ManageOrderUseCase`, `ManageProductUseCase`).
- **Infrastructure.** Contains the adapters for the web layer (Spring MVC
  Controllers), database persistence (Spring Data JPA), and security
  configurations (Spring Security).

## Technologies Used

- **Language:** Java 26
- **Framework:** Spring Boot 4.1.0
- **Security:** Spring Security & JJWT (JSON Web Tokens)
- **Persistence:** Spring Data JPA & Hibernate
- **Database:** MariaDB
- **Build Tool:** Gradle (Kotlin DSL)
- **Boilerplate Reduction:** Project Lombok

## Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) 26 or compatible version.
- MariaDB server running locally on port `3306`.
- An active database named `ecommerce`.

## Installation and Execution

### Database setup

Log into your local MariaDB instance and execute the following command:

```sql
CREATE DATABASE ecommerce;
```

### Clone the repository

```bash
git clone 
cd 
```

### Run the application

Use the Gradle wrapper to build and start the application. Spring Boot will
automatically generate the database schema based on the entities.

Give permissions to the file.

```bash
./gradlew clean bootRun
```

The server will start on `http://localhost:8080`

## API Testing Workflow

To interact with the API, it is recommended to use Postman or any similar API
client. The endpoints are secured; therefore, a specific operational flow must be
followed.

### 1. User registration

Create a new user in the system to obtain credentials.

- **Method:** `POST`
- **Endpoint:** `/api/auth/register`
- **Body (JSON):**

  ```json
  {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "securePassword123"
  }
  ```

- **Response:** Returns an authentication token (`AuthResponseDto`).

### 2. Authentication (Login)

If the user is already registered, authenticate to receive a fresh token.

- **Method:** `POST`
- **Endpoint:** `/api/auth/login`
- **Body (JSON):**

  ```json
  {
      "email": "john.doe@example.com",
      "password": "securePassword123"
  }
  ```

- **Response:** Returns a JWT string. Copy this token.

### 3. Authorization header setup

For all subsequent requests (except `GET /api/products`), you must include the
token in the HTTP headers.

- **Header Name:** `Authorization`
- **Header Value:** `Bearer <your_copied_jwt_token>`

### 4. Product Management

Add inventory to the store. Requires authentication.

- **Method:** `POST`
- **Endpoint:** `/api/products`
- **Body (JSON):**

  ```json
  {
      "name": "Mechanical Keyboard",
      "description": "RGB Mechanical Keyboard with Red Switches",
      "price": 75.50,
      "stock": 50,
      "imageUrl": "[http://example.com/keyboard.jpg](http://example.com/keyboard.jpg)"
  }
  ```

### 5. Order Creation

Simulate a purchase. The system will automatically validate the requested
quantity against the available stock and deduct it if the transaction is successful.

- **Method:** `POST`
- **Endpoint:** `/api/orders`
- **Body (JSON):**

  ```json
  {
      "lines": [
          {
              "productId": 1,
              "quantity": 2
          }
      ]
  }
  ```

- **Response:** Returns the confirmed order with the final calculated total
  amount and historical prices.
