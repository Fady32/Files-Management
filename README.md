# File Management System

## Description

The **File Management System** is a Spring Boot-based REST API designed to manage files, folders, and spaces. It supports user authentication, file uploads, folder creation, permission management, and file metadata. The system organizes files in a hierarchical tree structure, where **Spaces** are the root, containing **Folders** that can hold **Files**. It provides features like uploading, downloading, permission-based access control, and metadata retrieval for each file.

The application is built with **Spring Boot**, **Spring Security**, **PostgreSQL**, and **Swagger UI** for API documentation. The system is also **Dockerized**, allowing easy deployment of both the application and database.

---

## Features

- **Auth Management**: login, and show users permissions.
- **File Management**: Upload, download, and manage files and folders.
- **Permissions Management**: Control user access to spaces, folders, and files.
- **File Metadata**: Retrieve metadata of files stored in the system.
- **Swagger Documentation**: Interactive API documentation for testing and exploration.
- **Database Integration**: PostgreSQL database used for storing file and permission data.
- **Authentication**: JWT-based authentication for secure access to the API.
- **Docker Support**: Dockerized Spring Boot app and PostgreSQL for easy deployment.

---

## Requirements

- **Java 17** (or higher) for building the Spring Boot application.
- **Maven** for dependency management.
- **PostgreSQL** for database management.
- **Docker** for containerization of both the application and database.
- **Spring Boot** for the application framework.
- **Spring Security** for JWT-based authentication.

---

## Getting Started

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/Fady32/Files-Management.git
cd file-management-system
```
## 2. Build the Application
Make sure you have Maven installed. Run the following command to build the project:
```bash
mvn clean install
```


##  3. Run the Application
Run the application with the following command:

```bash
mvn spring-boot:run
```

### Deployment

To Execute the springboot application please the below docker compose file:

First, navigate to this docker-compose file

```
- docker-compose up -d
```

To stop the container

```
-  docker-compose down
```


### By default, the application will be available at:

**Swagger UI: http://localhost:8080/swagger-ui/index.html** **(use Swagger UI to test the API)**


## Some Of APIs

### User Management
- **POST /auth/login**: Login and get a JWT token. 
####  admin@email.com ( Internal user ) has permissions of admin group

### File Management
- **POST /api/items/create-space**: Create a new space (directory).
- **POST /api/items/create-folder/{spaceName}**: Create a new folder inside a space.
- **POST /api/items/create-file/upload**: Upload a file to a space or folder.
- **GET /api/items/file/getMetaData**: Fetch metadata of a file based on its ID.
- **GET /api/items/file/download**: Download a file.

### Permission Management
- **POST /api/permissions**: Create a new permission.

## Example Request for Creating a Space

You can use the following `curl` command to create a new space:

```bash
curl -X POST http://localhost:8080/api/items/create-space \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <your-jwt-token>" \
-d '{
  "spaceName": "space",
  "permissionGroupName": "admin"
}
}'
```

## Diagrams : 

#### ERD Diagram

<img height="400" src="file-management\src\main\resources\static\ERD%20diagram.png" width="400"/>