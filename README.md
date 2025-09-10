# 🐾 Pet Registry System

A modern Spring Boot web application for managing pet records with sophisticated role-based access control and user management capabilities.

**Author:** it21542 - Antonis Rouseas

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Prerequisites](#-prerequisites)
- [Quick Start](#-quick-start)
- [User Accounts](#-user-accounts)
- [API Documentation](#-api-documentation)
- [API Endpoints](#-api-endpoints)
- [Troubleshooting](#-troubleshooting)

---

## 🎯 Overview

The Pet Registry System is a comprehensive web application designed to manage pet records efficiently. It provides different access levels for administrators, citizens, and veterinarians, ensuring secure and organized pet data management.

### What This Application Does

- **Pet Registration**: Citizens can register their pets with detailed information
- **Medical Record Management**: Veterinarians can maintain and update pet medical histories
- **User Administration**: Admins can manage user accounts and system settings
- **Role-Based Security**: Different access levels ensure data privacy and security
- **Real-time Dashboard**: Live statistics and data visualization

---

## 📋 Prerequisites

Before running the application, ensure you have:

- ☑️ **Java 11+** - [Download here](https://adoptium.net/)
- ☑️ **Maven 3.6+** - [Installation guide](https://maven.apache.org/install.html)
- ☑️ **Docker** - [Get Docker](https://www.docker.com/get-started)
- ☑️ **Git** - [Download Git](https://git-scm.com/downloads)

### Verify Installation

```bash
java -version    # Should show Java 11+
mvn -version     # Should show Maven 3.6+
docker --version # Should show Docker version
```

---

## 🚀 Quick Start

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd Distributed-Systems-Assignment-master
```

### Step 2: Start MySQL Database

Launch MySQL using Docker:

```bash
docker run --name mysqldb -v mysqldbvol:/var/lib/mysql -p 3307:3306 -e MYSQL_USER=jerry -e MYSQL_PASSWORD=mypassword -e MYSQL_DATABASE=ds_db -e MYSQL_ROOT_PASSWORD=mypassword --rm -d mysql
```

> **📝 Note:** The application is configured to connect to MySQL on port **3307** with username `jerry` and password `mypassword`.

### Step 3: Build and Run

```bash
# Clean and compile the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

### Step 4: Access the Application

🌐 **Application URL**: [http://localhost:8080/login](http://localhost:8080/login)

### Step 5: API Documentation

📚 **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
📋 **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 👤 User Accounts

The application comes with pre-configured user accounts for immediate testing:

### Default Users

| ID Number | Username | Password | Role | Access Level |
|:---------:|:--------:|:--------:|:----:|:-------------|
| `000000001` | `admin` | `pass` | **ADMIN** | 🔧 Full system access, user management, all features |
| `000000002` | `citizen` | `pass` | **CITIZEN** | 🏠 Pet registration, view own pets, basic features |
| `000000004` | `john_doe` | `pass` | **CITIZEN** | 🏠 Pet registration, view own pets, basic features |
| `000000005` | `jane_smith` | `pass` | **CITIZEN** | 🏠 Pet registration, view own pets, basic features |
| `000000003` | `vet` | `pass` | **VET** | 🩺 Pet medical records, health approvals |
| `000000006` | `dr_wilson` | `pass` | **VET** | 🩺 Pet medical records, health approvals |

### Role Capabilities

#### 🔧 **ADMIN**
- Create, modify, and delete users
- Access admin dashboard with system statistics
- View all pets and users
- Manage system settings

#### 🏠 **CITIZEN**
- Register new pets
- View and modify own pet information
- Access citizen dashboard
- Update personal profile

#### 🩺 **VET**
- View all pet medical records
- Update pet health information
- Approve/disapprove medical records
- Access veterinary dashboard

---

## 📚 API Documentation

The Pet Registry System includes comprehensive API documentation powered by **Swagger/OpenAPI 3.0**.

### Interactive API Documentation

🌐 **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

The Swagger UI provides:
- **Interactive API Testing**: Test all endpoints directly from the browser
- **Real-time Documentation**: Auto-generated from code annotations
- **Request/Response Examples**: See exactly what data to send and expect
- **Authentication Testing**: Test with different user roles
- **Schema Definitions**: Complete data models for Users and Pets

### API Specification

📋 **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Use this endpoint to:
- Import into Postman or other API testing tools
- Generate client libraries in different programming languages
- Integration with other development tools

### API Categories

The API is organized into three main categories:

1. **🔒 User Management** - Create, modify, and manage user accounts
2. **🐾 Pet Management** - Complete CRUD operations for pet records
3. **🌐 Template Controller** - Web interface compatible endpoints

### Authentication for API Testing

All protected endpoints require authentication. Use these credentials in Swagger UI:

- **Admin**: `admin` / `pass` - Full access to all endpoints
- **Citizen**: `citizen` / `pass` - Pet creation and management
- **Vet**: `vet` / `pass` - Medical record updates and approvals

> **💡 Tip**: Access the Swagger UI while the application is running to explore all available endpoints with live documentation and testing capabilities.

---

## 🔗 API Endpoints

### Public Endpoints
- `GET /login` - Login page
- `GET /register` - User registration
- `POST /users/new` - Create new user

### Protected Endpoints

#### User Management
- `GET /users/list` - Get all users (Admin only)
- `POST /users/modify` - Modify user (Admin only)
- `GET /debug/current-user` - Current user info

#### Pet Management
- `GET /api/pets/count` - Get pet count
- `POST /pets/new` - Register new pet
- `GET /pets/list` - List pets (role-dependent)

#### Dashboard Routes
- `GET /admin` - Admin dashboard
- `GET /citizen` - Citizen dashboard
- `GET /vet` - Veterinarian dashboard

---

## 🔧 Configuration

### Database Configuration

The application connects to MySQL with these settings:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3307/ds_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=jerry
spring.datasource.password=mypassword

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.sql.init.mode=always
```

### Security Configuration

- **NoOpPasswordEncoder**: Used for development (passwords stored in plain text)
- **Form-based Authentication**: Login form with session management
- **Role-based Authorization**: URL patterns protected by user roles
- **Session Timeout**: 5-minute session timeout for security

---

## 🐛 Troubleshooting

### Common Issues & Solutions

#### 🔴 Database Connection Failed
```bash
# Check if MySQL container is running
docker ps

# Restart MySQL container
docker stop mysqldb
docker run --name mysqldb -v mysqldbvol:/var/lib/mysql -p 3307:3306 -e MYSQL_USER=jerry -e MYSQL_PASSWORD=mypassword -e MYSQL_DATABASE=ds_db -e MYSQL_ROOT_PASSWORD=mypassword --rm -d mysql
```

#### 🔴 Port 8080 Already in Use
```bash
# Option 1: Find and kill the process
netstat -ano | findstr :8080
# Kill the process ID shown

# Option 2: Use different port
# Add to application.properties:
server.port=8081
```

#### 🔴 Build Failures
```bash
# Clean rebuild
mvn clean compile

# Check Java version
java -version  # Must be 11+

# Verify Maven configuration
mvn -version
```

#### 🔴 Foreign Key Constraint Errors
This usually indicates data initialization issues. The application now uses `data.sql` for consistent data loading.

### Debug Mode

Enable debug logging by adding to `application.properties`:
```properties
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG
```

---

## 🚢 Docker Deployment (Optional)

For containerized deployment:

```bash
# Build Docker image
docker build -t pet-registry .

# Run with Docker Compose
docker-compose up
```

---

**Happy Pet Managing! 🐾**
