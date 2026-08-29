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

-------------------------------------------------------
## Day 2 — Architecture Understanding

### Request Flow: Create User

When a client sends:

POST /api/users

the request follows this flow:

Client
↓
UserController
↓
UserRequest DTO
↓
UserService
↓
Business Logic / Validation
↓
User Entity
↓
UserRepository
↓
JPA / Hibernate
↓
MySQL Database
↓
UserRepository
↓
UserService
↓
UserResponse DTO
↓
UserController
↓
JSON Response
↓
Client

### Important Concepts

- UserRequest is used for incoming API data.
- UserResponse is used for outgoing API data.
- DTOs do not perform business logic or database operations.
- UserService contains business logic.
- UserRepository handles database access.
- User Entity represents the database record.
- GlobalExceptionHandler handles exceptions when an error occurs.

### Exception Flow

If a business rule fails, for example a duplicate email:

UserService
↓
DuplicateEmailException
↓
GlobalExceptionHandler
↓
ErrorResponse
↓
Client

The exception handler is an error path, not part of the normal successful request flow.

### Interview Explanation

Controller receives the request → 
DTO carries the API data → 
Service applies business logic → 
Repository accesses the database → 
Entity represents the database record → 
Service prepares the response DTO → 
Controller returns the response.

=============================================
# Day 2 Checkpoint

## Architecture Understanding

### User Creation Request Flow

Client
↓
UserController
↓
UserRequest DTO
↓
UserService
↓
Business Logic / Validation
↓
User Entity
↓
UserRepository
↓
JPA / Hibernate
↓
MySQL Database
↓
UserRepository
↓
UserService
↓
UserResponse DTO
↓
UserController
↓
ApiResponse
↓
HTTP Response
↓
Client

### Important Concepts

- Controller handles HTTP requests and responses.
- UserRequest carries incoming API data.
- UserResponse carries outgoing user data.
- Service contains business logic.
- Repository handles database access.
- Entity represents the database record.
- GlobalExceptionHandler handles exceptions.
- DTOs are used to separate API data from database entities.

---

## API Response Standardization

Created:

`common/ApiResponse.java`

The generic `ApiResponse<T>` provides a common structure for successful API responses.

Structure:

- success
- message
- data
- timestamp

### Generic Type

`ApiResponse<T>` allows the same response wrapper to contain different types of data.

Examples:

`ApiResponse<UserResponse>`

`ApiResponse<List<UserResponse>>`

---

## Updated APIs

### POST /api/users

Success response:

`201 Created`

Uses:

`ApiResponse<UserResponse>`

Message:

`User created successfully`

---

### GET /api/users

Success response:

`200 OK`

Uses:

`ApiResponse<List<UserResponse>>`

Message:

`Users fetched successfully`

---

### GET /api/users/{id}

Success response:

`200 OK`

Uses:

`ApiResponse<UserResponse>`

Message:

`User fetched successfully`

If user does not exist:

`404 Not Found`

Handled by:

`UserNotFoundException`

and

`GlobalExceptionHandler`

---

### PUT /api/users/{id}

Success response:

`200 OK`

Uses:

`ApiResponse<UserResponse>`

Message:

`User updated successfully`

The controller wraps the service result inside `ApiResponse`.

---

### DELETE /api/users/{id}

Success response:

`204 No Content`

No response body is returned.

Flow:

Client
↓
UserController
↓
UserService
↓
UserRepository
↓
MySQL
↓
204 No Content

If the user does not exist:

`404 Not Found`

Handled by:

`UserNotFoundException`

and

`GlobalExceptionHandler`

---

## Key Learning

Successful APIs that return data use the standard `ApiResponse<T>` wrapper.

DELETE uses `204 No Content` because the operation succeeds without returning a response body.

`ResponseEntity` is used by the controller to control the HTTP status and response body.

---

## Day 2 Testing

Tested successfully using Postman:

- POST user → `201 Created`
- GET all users → `200 OK`
- GET existing user → `200 OK`
- GET non-existing user → `404 Not Found`
- PUT user → `200 OK`
- DELETE existing user → `204 No Content`
- DELETE/GET non-existing user → `404 Not Found`

All tested scenarios are working correctly.