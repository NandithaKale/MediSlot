# MediSlot 🏥
**Smart Doctor Appointment & Availability Management System**

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![JWT](https://img.shields.io/badge/JWT-Auth-red)
![Railway](https://img.shields.io/badge/Deployed-Railway-purple)

## 🌐 Live API
**Base URL:** https://medislot-production-d881.up.railway.app

---

## 💡 Why I Built This
Booking a doctor's appointment should be simple. No phone calls, no queues, no confusion about availability. MediSlot is my attempt at solving this — a clean backend API that handles everything from doctor registration to appointment booking, built with security and real-world usability in mind.

---

## ✨ What It Does
- Patients can register, search doctors by specialization, and book appointments
- Doctors can manage their availability and view their schedule
- Smart conflict detection prevents double booking automatically
- Role based access ensures patients, doctors, and admins only see what they should
- JWT tokens keep every request secure without sessions

---

## 🛠️ Tech Stack
| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.5.0 |
| Security | Spring Security + JWT |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA |
| Build Tool | Maven |
| Deployment | Railway |

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register as patient or doctor |
| POST | /api/auth/login | Login and get JWT token |

### Doctors
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/doctors | Get all doctors |
| GET | /api/doctors?specialization=Cardiology | Search by specialization |
| GET | /api/doctors/{id} | Get doctor by ID |

### Appointments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/appointments/book | Book an appointment |
| GET | /api/appointments/my/{patientId} | View my appointments |
| DELETE | /api/appointments/{id} | Cancel an appointment |

---

## 🚀 Run Locally
```bash
# Clone the repo
git clone https://github.com/NandithaKale/MediSlot.git
cd MediSlot

# Update src/main/resources/application.properties
# with your MySQL username and password

# Run
mvn spring-boot:run

# Test
http://localhost:8080/api/doctors
```

---

## 📂 Project Structure
```
src/main/java/com/nanditha/medislot/
├── controller/     — REST Controllers
├── model/          — JPA Entities
├── repository/     — Spring Data Repositories
├── service/        — Business Logic
├── security/       — JWT Filter and Util
├── config/         — Security Configuration
└── dto/            — Data Transfer Objects
```

---

## 👩‍💻 About Me
I'm Nanditha, a Computer Science student at BMS Institute of Technology and Management. I built this project to get hands-on with Spring Boot, REST APIs, and JWT security — and to build something that solves a real problem.

📧 nanditakale64@gmail.com
🔗 [LinkedIn](https://linkedin.com/in/nanditha-kale)
🐙 [GitHub](https://github.com/NandithaKale)
