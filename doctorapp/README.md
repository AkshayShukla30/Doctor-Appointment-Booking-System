

# 🏥 Doctor Appointment Booking System

A backend REST API built with Java and Spring Boot for managing doctor appointments. The application provides secure authentication, appointment scheduling, doctor availability management, online payments, email notifications, reviews, and an admin dashboard.

---

## ✨ Features

- Secure authentication using JWT and Spring Security
- Role-based access control for Patients, Doctors, and Admin
- Doctor registration with specialization and consultation fee
- Doctor slot management
- Appointment booking and cancellation
- Prevents double booking using optimistic locking
- Razorpay payment integration
- Email notifications for registration and appointments
- Doctor reviews and ratings
- Symptom-based doctor recommendation
- Admin analytics dashboard
- REST APIs documented with Swagger
- Docker support
- CI/CD workflow using GitHub Actions
- Unit testing with JUnit 5 and Mockito

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- Maven
- Razorpay
- Spring Mail
- Swagger (OpenAPI)
- JUnit 5
- Mockito
- Docker
- GitHub Actions

---

## 👥 User Roles

### Patient

- Register and login
- Browse doctors
- Book appointments
- Cancel appointments
- Make payments
- Submit reviews and ratings

### Doctor

- Register with specialization
- Manage available slots
- View appointments
- Mark appointments as completed

### Admin

- View dashboard
- Monitor appointments
- Track revenue
- View platform statistics

---

## 📌 Key Features

| Feature                   | Description                                                   |
| ------------------------- | ------------------------------------------------------------- |
| Authentication            | Secure JWT-based authentication with role-based authorization |
| Appointment Booking       | Patients can book and cancel appointments                     |
| Slot Management           | Doctors can publish and manage available time slots           |
| Double Booking Protection | Prevents multiple users from booking the same slot            |
| Payments                  | Integrated Razorpay payment gateway                           |
| Email Notifications       | Registration, booking, and cancellation emails                |
| Doctor Recommendation     | Suggests doctors based on symptoms                            |
| Reviews & Ratings         | Patients can rate completed appointments                      |
| Admin Dashboard           | Revenue, appointments, doctors, patients and analytics        |
| Swagger Documentation     | Interactive API documentation                                 |
| Docker Support            | Containerized application                                     |
| CI/CD                     | Automatic build and test workflow                             |

---

## 📂 Project Structure

```
Doctor-Appointment-Booking-System
│
├── .github/
│   └── workflows/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.doctorapp/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
└── .gitignore
```

---

## 📚 API Overview

### Authentication

```
POST /auth/register
POST /auth/login
```

### Doctors

```
GET /doctors
GET /doctors/{doctorId}
GET /doctors/specialization/{specialization}
GET /doctors/{doctorId}/slots
GET /doctors/{doctorId}/reviews
```

### Doctor APIs

```
POST /doctor/slots
GET /doctor/slots/mine
GET /doctor/appointments
PUT /doctor/appointments/{id}/complete
```

### Patient APIs

```
POST /appointments/book
GET /appointments/mine
PUT /appointments/{id}/cancel
POST /reviews
```

### Payments

```
POST /payment/create-order/{appointmentId}
POST /payment/verify
```

### Doctor Recommendation

```
GET /recommend/doctors?symptoms=...
```

### Admin

```
GET /admin/dashboard
```

---

## ⚙️ Running Locally

### 1. Clone the repository

```bash
git clone https://github.com/AkshayShukla30/Doctor-Appointment-Booking-System.git
```

### 2. Create MySQL database

```sql
CREATE DATABASE doctorappointmentdb;
```

### 3. Configure application.properties

Update your database credentials and other required configurations.

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secret=your_secret_key

razorpay.key=your_key
razorpay.secret=your_secret
```

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application will start on

```
http://localhost:8080
```

Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 🐳 Running with Docker

Build and start the application

```bash
docker compose up --build
```

---

## 🧪 Testing

Run all unit tests

```bash
mvn test
```

---

## 🚀 Future Improvements

- Video consultation
- Appointment reminders
- Digital prescriptions
- Search doctors by location
- Multi-language support
- Mobile application integration

---

## 👨‍💻 Author

**Akshay Shukla**

📧 akshayshukla466@gmail.com

🔗 GitHub: https://github.com/AkshayShukla30

🔗 LinkedIn: https://www.linkedin.com/in/akshayshukla30/

---

## 📄 License

This project is intended for learning and portfolio purposes.
