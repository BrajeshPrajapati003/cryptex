What is Open Session in View (OSIV)?

When a request comes in:

Client
│
▼
Controller
▼
Service
▼
Repository
▼
Database

Hibernate opens a Session (database connection + persistence context).

Normally, that session should close after the service finishes.