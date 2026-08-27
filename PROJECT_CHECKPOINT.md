# Food Delivery Platform — Project Checkpoint

## Current Progress

- Current Day: Day 1
- Current Step: Step 33
- Status: Day 1 Complete

---

## Day 1 — Completed Work

### Project Setup
- Spring Boot project created
- Maven configured
- Java 17 configured
- MySQL database configured
- Application successfully connected to MySQL
- Application running on port 8080

### User Module

Implemented:

- User Entity
- UserRequest DTO
- UserResponse DTO
- UserRepository
- UserService
- UserController

### User APIs

POST /api/users
- Create user
- Duplicate email protection
- Returns 201 CREATED

GET /api/users
- Get all users
- Returns 200 OK

GET /api/users/{id}
- Get user by ID
- Returns 200 OK
- Returns 404 when user doesn't exist

PUT /api/users/{id}
- Update user
- Returns 200 OK
- Duplicate email protection
- Allows user to keep their own email

DELETE /api/users/{id}
- Delete user
- Returns 204 NO CONTENT
- Returns 404 when user doesn't exist

---

## Exception Handling

Implemented:

- UserNotFoundException
- DuplicateEmailException
- Global exception handling

HTTP status codes:

- 200 OK
- 201 CREATED
- 204 NO CONTENT
- 404 NOT FOUND
- 409 CONFLICT

---

## Repository Methods

Current UserRepository contains:

- existsByEmail(String email)
- existsByEmailAndIdNot(String email, Long id)

---

## Important Business Rules

1. Email must be unique.
2. Duplicate email during creation returns 409 CONFLICT.
3. Duplicate email during update returns 409 CONFLICT.
4. A user can keep their existing email during update.
5. Updating/deleting a non-existing user returns 404 NOT FOUND.

---

## Testing

User APIs tested successfully using Postman.

Tested:

- Create user
- Get all users
- Get user by ID
- Update user
- Delete user
- Duplicate email during creation
- Duplicate email during update
- Non-existing user
- 204 DELETE response

---

## Coding Practices

- Controller handles HTTP requests.
- Service contains business logic.
- Repository handles database operations.
- DTOs are used instead of exposing entities directly.
- Custom exceptions are used for business errors.
- Meaningful comments are added to important code sections.

---

## Day 2 Starting Point

Before starting new functionality:

1. Review Day 1 code.
2. Review project structure.
3. Review exception handling.
4. Review validation.
5. Improve API response structure.
6. Plan and begin the next domain module.

---

## Git

Day 1 work should be committed and pushed with:

git add .
git commit -m "Day 1: Complete User CRUD with validation and exception handling"
git push origin main