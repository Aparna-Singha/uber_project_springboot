# 🚕 Ride Sharing Backend

A mini ride-sharing backend built with Spring Boot, MongoDB, and JWT Authentication.

## 🏗️ Tech Stack

- **Spring Boot 3.2.0**
- **MongoDB** (NoSQL Database)
- **JWT** (JSON Web Tokens for authentication)
- **Spring Security**
- **Jakarta Bean Validation**
- **Maven**

## 📁 Project Structure

```
src/main/java/org/example/rideshare/
├── model/              # Entity classes (User, Ride)
├── repository/         # MongoDB repositories
├── service/            # Business logic
├── controller/         # REST endpoints
├── config/             # Security & JWT configuration
├── dto/                # Request/Response DTOs
├── exception/          # Custom exceptions & global handler
└── util/               # Utility classes (JwtUtil)
```

## 🚀 Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.6+
- MongoDB running on `localhost:27017`

### Run the Application

```bash
# Navigate to project directory
cd rideshare

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## 📋 API Endpoints

### 🔓 Public Endpoints (No Authentication)

#### Register User
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
```

#### Register Driver
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "driver1",
  "password": "abcd",
  "role": "ROLE_DRIVER"
}
```

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "1234"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 🔒 Protected Endpoints (Require JWT Token)

#### Create Ride (USER only)
```bash
POST /api/v1/rides
Authorization: Bearer <token>
Content-Type: application/json

{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
```

#### View My Rides (USER only)
```bash
GET /api/v1/user/rides
Authorization: Bearer <token>
```

#### View Pending Ride Requests (DRIVER only)
```bash
GET /api/v1/driver/rides/requests
Authorization: Bearer <token>
```

#### Accept Ride (DRIVER only)
```bash
POST /api/v1/driver/rides/{rideId}/accept
Authorization: Bearer <token>
```

#### Complete Ride (USER or DRIVER)
```bash
POST /api/v1/rides/{rideId}/complete
Authorization: Bearer <token>
```

## 🧪 Testing with CURL

### 1. Register a User
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"1234","role":"ROLE_USER"}'
```

### 2. Register a Driver
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"driver1","password":"abcd","role":"ROLE_DRIVER"}'
```

### 3. Login as User
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"1234"}'
```
Copy the token from response.

### 4. Create a Ride
```bash
curl -X POST http://localhost:8081/api/v1/rides \
-H "Authorization: Bearer <USER_TOKEN>" \
-H "Content-Type: application/json" \
-d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'
```

## 🔑 Features

✅ User Registration with role-based access (USER/DRIVER)  
✅ JWT-based authentication and authorization  
✅ Password encryption with BCrypt  
✅ Input validation with Jakarta Bean Validation  
✅ Global exception handling  
✅ MongoDB integration  
✅ Clean architecture (Controller → Service → Repository)

## 📊 Database Collections

### users
```json
{
  "_id": "ObjectId",
  "username": "john",
  "password": "$2a$10$...",
  "role": "ROLE_USER"
}
```

### rides
```json
{
  "_id": "ObjectId",
  "userId": "user_id",
  "driverId": "driver_id",
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar",
  "status": "REQUESTED",
  "createdAt": "2025-01-20T10:00:00Z"
}
```

## 🛡️ Security

- Passwords are hashed using BCrypt
- JWT tokens expire after 24 hours
- Role-based access control for endpoints
- Stateless authentication (no sessions)

## 📝 Error Response Format

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Username is required",
  "timestamp": "2025-01-20T12:00:00Z"
}
```

## 👨‍💻 Author

Built with ☕ and 🚀
