# Automated Commercial Insurance Claim Verification & Settlement Engine

## Project Overview

The Automated Commercial Insurance Claim Verification & Settlement Engine is a microservices-based application designed to simplify and automate the insurance claim verification and settlement process.

The system uses independent microservices to manage insurance claims, policies, and settlements. It provides centralized request routing and service discovery to enable communication between different services.

## Objectives

- Automate the insurance claim verification process.
- Manage insurance policies efficiently.
- Simplify claim settlement operations.
- Improve communication between microservices.
- Provide a scalable and maintainable architecture.

## Technologies Used

- **Backend:** Java, Spring Boot
- **Architecture:** Microservices Architecture
- **Service Discovery:** Netflix Eureka
- **API Routing:** Spring Cloud Gateway
- **Build Tool:** Maven
- **Database:** Add your database technology here
- **Version Control:** Git and GitHub
- **API Testing:** Postman

## Project Architecture

The project consists of the following microservices:

### 1. API Gateway
- Acts as the single entry point for client requests.
- Routes requests to the appropriate microservices.

### 2. Eureka Server
- Provides service discovery.
- Maintains a registry of available microservices.

### 3. Claim Service
- Manages insurance claim information.
- Handles claim-related operations and verification.

### 4. Policy Service
- Manages insurance policy information.
- Handles policy-related operations.

### 5. Settlement Service
- Manages claim settlement operations.
- Handles settlement-related processing.

## Project Structure

```text
SOA_PROJECT/
│
├── api-gateway/
│
├── claim-service/
│
├── eureka-server/
│
├── policy-service/
│
├── settlement-service/
│
└── .gitignore
```

## System Architecture Flow

```text
          Client / User
                |
                v
          API Gateway
                |
       -------------------
       |        |        |
       v        v        v
   Claim     Policy   Settlement
   Service   Service   Service
       \        |        /
        \       |       /
         Eureka Server
        (Service Discovery)
```

## Installation and Setup

### Prerequisites

- Java JDK
- Maven
- Spring Tool Suite (STS) or IntelliJ IDEA
- Git
- MySQL or configured database

### Steps to Run the Project

1. Clone the repository:

```bash
git clone https://github.com/Vardhan2858/SOA_PROJECT.git
```

2. Open the project in Spring Tool Suite.

3. Import the individual microservices as Maven projects.

4. Configure the database credentials in the respective `application.properties` files.

5. Start the Eureka Server.

6. Start the individual microservices:
   - Claim Service
   - Policy Service
   - Settlement Service

7. Start the API Gateway.

8. Verify service registration through the Eureka Dashboard.

9. Test the API endpoints using Postman.

## Repository

GitHub: https://github.com/Vardhan2858/SOA_PROJECT

## Team Members

Add your team members' names and contributions here.

## Future Enhancements

- Implement JWT-based authentication and authorization.
- Add automated claim validation.
- Integrate notification services.
- Improve monitoring and logging.
- Implement advanced fraud detection mechanisms.

## License

This project is developed for academic purposes.
