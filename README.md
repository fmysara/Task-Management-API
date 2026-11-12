# Task Management API

## Project Description
A RESTful Task Management API built using Java and Spring Boot. It allows users to register, log in, and manage their personal tasks securely.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT Authentication
- BCrypt Password Encryption
- MySQL
- Maven
- Postman

## Features
- User registration and login
- JWT-based authentication
- Password encryption using BCrypt
- Create, view, update, and delete tasks
- Tasks associated with individual users
- Pagination and task status filtering
- Input validation
- Global exception handling
- Automated unit tests

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | User login |
| POST | /api/tasks | Create task |
| GET | /api/tasks | Get all tasks |
| GET | /api/tasks/{id} | Get task by ID |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Delete task |

## Database
MySQL database: `task_manager`

Configure your database credentials in `application.properties`.

## Running the Project
1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure MySQL credentials.
4. Create the `task_manager` database.
5. Reload Maven dependencies.
6. Run `TaskManagementApiApplication.java`.

The API runs at:

`http://localhost:8080`

## Testing
The project includes five automated service unit tests using JUnit and Mockito.

API endpoints were also tested using Postman.