# API Automation Framework

API test automation framework for a booking management REST API.

## Overview

This project demonstrates a structured approach to API test automation with a focus on maintainability, reusable components, and clean test architecture.

## System Under Test

REST API for booking management.

Covered functionality:
- Booking CRUD operations
- Authentication (token-based)
- Protected endpoints (update/delete)
- Validation of invalid requests

---

## Architecture

tests → steps → api → models → config

- tests - high-level scenarios  
- steps - business logic  
- api - HTTP layer  
- models - request/response DTO  
- config - environment setup  

## Tech Stack

- Java 17  
- Gradle  
- JUnit 5  
- RestAssured  
- Allure  

## Getting Started

### Clone repository

```bash
git clone https://github.com/Forostyanura94/java-automation-framework.git
cd java-automation-framework
```
---

## Setup environment

Default values can be found in the API documentation: 
https://restful-booker.herokuapp.com/apidoc/index.html#api-Auth-CreateToken

```bash
TEST_ENV=<env>
BASE_URI=<url>
ADMIN_USERNAME=<username>
ADMIN_PASSWORD=<password>
```
### Run tests
./gradlew clean test

### Allure report locally
allure serve build/allure-results

### CI
Tests are executed via GitHub Actions with Allure results as artifacts.
