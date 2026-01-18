# 🏆 Premier League — Spring Boot Backend (Java 21)

Premier League is a production-ready **Spring Boot 3.5.x** backend built with **Java 21**, featuring secure authentication, Redis caching, rate limiting, email notifications, and fully interactive API documentation using Swagger (OpenAPI 3).  
This project is designed with real-world engineering standards and is fully CI/CD enabled using **GitHub Actions → Railway**.

---

## 🚀 Live Demo & API Documentation

Swagger UI (Live):  
**http://premierleague-backend-production.up.railway.app/swagger-ui/index.html**(For some technical problems this page is not working currently...)

OpenAPI Spec (Live):  
**http://premierleague-backend-production.up.railway.app/v3/api-docs**(For some technical problems this page is not working currently...)


---

## 🔥 Why This Project Stands Out
This project demonstrates **industry-grade backend engineering**, showing skills that matter to hiring teams at **Google, Amazon, Microsoft, Meta, Netflix**, and modern product-based companies:

- Built using **Java 21 (LTS)** and **Spring Boot 3.5.x**
- Fully secure authentication system:
  - **JWT Authentication**
  - **OAuth2 Google Login**
  - **Role-based Access Control (RBAC)**
- **Redis caching** for performance  
- **Rate limiting** for API protection (Bucket4j)
- **SendGrid email** integration (Welcome emails)
- **Swagger / OpenAPI** for interactive documentation
- **CI/CD pipeline** using GitHub Actions → Railway deployment
- Clean modular architecture: Controllers → Services → Repositories → DTOs

---

## 📌 Key Features

### 🔐 Authentication & Security
- JWT-based login & token validation  
- OAuth2 login using Google  
- Role-based API access (`ADMIN`, `USER`)  
- Secure password hashing with BCrypt  
- Centralized authentication filter (`JwtAuthFilter`)

### ⚙️ Performance & Production Features
- **Redis caching** (Spring Data Redis)
- **Rate limiting** using Bucket4j   
- **Exception handling layer**

### 📡 API Documentation
- Complete API docs using *springdoc-openapi*  
- Global JWT support in Swagger  
- Method-level annotations (@Operation, @SecurityRequirements)

### ✉️ Email Service
- Integrated SendGrid SDK  
- HTML email template support  
- Welcome email hooks

### 🔄 CI/CD Pipeline
- GitHub Actions workflow included  
- Builds project → Deploys to Railway using CLI  
- Environment variables handled securely

---

## 🧰 Tech Stack

| Category | Technology |
|---------|------------|
| Language | **Java 21** |
| Framework | **Spring Boot 3.5.x** |
| Security | JWT, OAuth2, Spring Security |
| Database | PostgreSQL (JPA + Hibernate) |
| Cache | Redis |
| Rate Limiting | Bucket4j |
| Docs | Swagger (OpenAPI 3) |
| Email | SendGrid |
| CI/CD | GitHub Actions |
| Deployment | Railway |
| Build Tool | Maven |

---

## 📂 Project Structure

```text
src/main/java/com/premierLeague/premier_Zone/
│
├── controller/      # REST controllers (Player, User, Auth)
├── service/         # Business logic layer
├── repository/      # JPA repositories
├── security/        # JWT, OAuth2, filters, rate limiting
├── dto/             # Request + response DTOs
├── config/          # Security, Redis, OpenAPI, Email
├── entity/          # JPA entities
└── mapper/          # Entity to DTO mapping


