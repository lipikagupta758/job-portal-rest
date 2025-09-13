# Spring Boot Job Post API

A RESTful API built with Spring Boot for managing job postings, including image upload support, authentication, and logging.

---

## Table of Contents
1. [Features](#features)
2. [Technologies](#technologies)
3. [Prerequisites](#prerequisites)
4. [Setup & Running](#setup--running)
    - [Local Setup](#local-setup)
    - [Docker Setup](#docker-setup)
5. [Database Configuration](#database-configuration)
6. [API Endpoints](#api-endpoints)
7. [Detailed Usage: POST Requests](#detailed-usage-post-requests)
8. [Notes](#notes)

---

## Features

- Create, read, update, and delete job posts
- Upload images with job posts (`multipart/form-data`)
- Search job posts by profile or description
- User authentication and authorization (Spring Security)
- Logging and cross-cutting concerns (Spring AOP)
- Uses Spring Data JPA and PostgreSQL

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring AOP
- Maven
- PostgreSQL
- Docker & Docker Compose

## Prerequisites
- Java 17 or above
- Maven
- PostgreSQL (for local setup)
- Docker & Docker Compose (for Docker setup)

---

## Setup & Running

### Local Setup
1. **Clone the repository**
    ```sh
    git clone <repository-url>
    cd <project-directory>
    ```
2. **Configure the database**
    - Edit `src/main/resources/application.properties` with your PostgreSQL settings.
    - Example:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/org
      spring.datasource.username=<your_db_username>
      spring.datasource.password=<your_db_password>
      ```
3. **Build and run the application**
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```
4. **Access the API** at `http://localhost:8081` (or as configured).

### Docker Setup
1. **Ensure Docker & Docker Compose are installed.**
2. **Edit environment variables in `docker-compose.yml` if needed.**
3. **Start the containers:**
    ```sh
    docker-compose up --build
    ```
4. **API available at** `http://localhost:8081`.
5. **Database available at** `localhost:5432` (inside Docker network as `postgres:5432`).

**Default Docker DB credentials:**
- DB name: `org`
- DB username: `<your_db_username>`
- DB password: `<your_db_password>`

---

## Database Configuration
- The schema is managed by Spring JPA (`spring.jpa.hibernate.ddl-auto=update`).
- For Docker, use:
  ```properties
  spring.datasource.url=jdbc:postgresql://postgres:5432/org
  ```
- For local, use:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/org
  ```
- Edit `src/main/resources/application.properties` as needed.

---

## API Usage Suggestion

> **Before using any authenticated endpoints, first register a user using the `/users/register` endpoint.**
> Use the registered username and password for authentication (e.g., Basic Auth) when calling other endpoints that require login.

---

## API Endpoints

### Authentication

- Most endpoints require authentication (Spring Security, basic auth or JWT if configured).
- Example login endpoint (if implemented):
  - `POST /login` — Authenticate user (see controller for details)

### Job Post Endpoints

| Method | Endpoint                | Description                        |
|--------|-------------------------|------------------------------------|
| POST   | `/jobs`                 | Add a new job post (use `multipart/form-data` for image upload; fields: `title`, `description`, `profile`, `image` [file]) |
| GET    | `/jobs`                 | List all job posts                 |
| GET    | `/jobs/{id}`            | Get a job post by ID               |
| PUT    | `/jobs`                 | Update a job post (send JSON body) |
| DELETE | `/jobs/{id}`            | Delete a job post by ID            |
| GET    | `/jobs/search?keyword=` | Search job posts by keyword        |

### User Endpoints

| Method | Endpoint         | Description                |
|--------|------------------|----------------------------|
| POST   | `/users/register`| Register a new user        |

---

## Detailed Usage: POST Requests

### 1. Add a New Job Post

**Endpoint:**  
`POST /jobPost`  
**Consumes:** `multipart/form-data`  
**Authentication:** Required (Basic Auth or JWT, as configured)

#### Request Structure
Send a `multipart/form-data` request with:
- `jobPost`: JSON string representing the job post details
- `image`: (Optional) Image file to upload

#### Required Fields in `jobPost` JSON
- `title` (string)
- `description` (string)
- `profile` (string)

#### Example cURL Request
```bash
curl -X POST http://localhost:8081/jobPost \
  -H "Authorization: Basic <base64-credentials>" \
  -F 'jobPost={"title":"Java Developer","description":"Spring Boot experience","profile":"Backend"}' \
  -F "image=@/path/to/image.jpg"
```

#### Example Postman Setup
- Method: POST
- URL: `http://localhost:8081/jobPost`
- Body: form-data
  - Key: `jobPost` (type: Text), value: `{ "title": "Java Developer", "description": "Spring Boot experience", "profile": "Backend" }`
  - Key: `image` (type: File), value: (select file, optional)
- Add `Authorization` header as required

#### Example Response
```json
{
  "postId": 1,
  "title": "Java Developer",
  "description": "Spring Boot experience",
  "profile": "Backend",
  "imageData": "...",
  ...
}
```

---

### 2. Register a New User

**Endpoint:**  
`POST /users/register`  
**Consumes:** `application/json`  
**Authentication:** Not required

#### Example Request
```json
POST /users/register
Content-Type: application/json

{
  "username": "john",
  "password": "password123",
  "role": "USER"
}
```

#### Example cURL
```bash
curl -X POST http://localhost:8081/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"password123","role":"USER"}'
```

#### Example Response
- **201 Created** or **200 OK**: User registered successfully
- **400 Bad Request**: If required fields are missing or user already exists

---

## Notes
- All `/jobPost` endpoints require authentication unless otherwise specified.
- For image upload, always use the `image` field in `multipart/form-data`.
- Use the correct endpoint paths as shown above.
- For troubleshooting, check logs and ensure DB is running and accessible.

---