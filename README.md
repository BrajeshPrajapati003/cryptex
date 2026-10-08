# CRYPTEX

> An AI-powered cryptocurrency intelligence platform built with Java, Spring Boot, and Next.js.

CRYPTEX is a full-stack project focused on building a scalable backend platform for cryptocurrency intelligence, portfolio management, and AI-assisted insights.

The project is being developed using a **microservices-oriented architecture**, with security, notifications, API gateway, persistence, and frontend components developed incrementally.

## Architecture

```text
                         ┌─────────────────────┐
                         │    Next.js Frontend │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     API Gateway     │
                         │   Spring Cloud      │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┴───────────────┐
                    ▼                               ▼
          ┌─────────────────┐             ┌─────────────────┐
          │  Auth Service   │             │ Notification    │
          │                 │             │ Service         │
          └─────────────────┘             └─────────────────┘
                    │                               │
                    ▼                               ▼
              ┌────────────┐                  ┌────────────┐
              │ PostgreSQL │                  │ PostgreSQL │
              └────────────┘                  └────────────┘
```

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Cloud Gateway
- PostgreSQL
- Maven
- MapStruct
- JUnit

### Frontend
- Next.js
- React
- TypeScript
- Tailwind CSS

### Development
- Git & GitHub
- IntelliJ IDEA
- Docker
- AWS

## Project Structure

```text
cryptex/
│
├── backend/
│   ├── common/
│   ├── gateway/
│   └── services/
│       ├── auth/
│       └── notification/
│
├── frontend/
│
└── notes/
```

## Current Development

The project is currently under active development.

The backend foundation includes work around:

- Authentication and authorization
- JWT-based security
- User management
- Email verification
- Password reset
- Refresh tokens
- Notification services
- Email templates
- API gateway
- PostgreSQL persistence
- DTO mapping
- Exception handling
- Automated testing
- Microservice architecture

The cryptocurrency intelligence, portfolio management, AI capabilities, and complete frontend experience are being developed incrementally.

## Goals

The long-term goal of CRYPTEX is to provide a platform capable of:

- Tracking cryptocurrency markets
- Managing cryptocurrency portfolios
- Providing market intelligence
- Generating AI-assisted insights
- Providing secure user accounts
- Delivering personalized notifications
- Scaling through a distributed backend architecture

## Status

🚧 **Under active development**

This README is intentionally kept concise while the project is being built. It will be replaced with complete project documentation once the major features and architecture are finalized.

## Author

**Brajesh Prajapati**

Java Backend Developer focused on:

- Java & Spring Boot
- Backend Architecture
- Microservices
- Databases
- Application Security
- System Design