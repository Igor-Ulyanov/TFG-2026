# REST API for Centralized Authentication & Authorization

A lightweight REST API that provides centralized authentication and role-based authorization (RBAC) for applications. Built with **Java**, **Spring Boot**, and **PostgreSQL**, it enables external services to delegate user authentication and permission management to a single backend.

## Project Status

This project was developed as my **Bachelor's Thesis (Final Degree Project)** in Computer Science. Its primary goal was to explore the design and implementation of a centralized authentication and authorization service while applying modern backend development practices.

The project should be considered a **Minimum Viable Product (MVP)**. It demonstrates the core concepts and functionality of an RBAC-based authentication system, but it is **not intended to be a production-ready IAM solution**. Features such as advanced security hardening, comprehensive testing, monitoring, high availability, and enterprise-level scalability were intentionally kept outside the project's scope.

## Features

- JWT-based stateless authentication
- Role-Based Access Control (RBAC)
- Multi-organization support
- CRUD operations for:
  - Users
  - Organizations
  - Roles
  - Permissions
- Permission verification endpoint (`/check`)
- Interactive API documentation with Swagger/OpenAPI
- Dockerized deployment with Docker Compose

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- PostgreSQL
- JWT
- Docker & Docker Compose
- OpenAPI / Swagger

## Overview

The API acts as a centralized authorization service that can be integrated into existing applications or microservice architectures. After authenticating a user and issuing a JWT, applications can validate permissions through a dedicated endpoint, allowing business services to remain independent of authorization logic.

Designed as a lightweight alternative to full Identity and Access Management (IAM) solutions, the project focuses on the core concepts of authentication and authorization while remaining easy to deploy and integrate.
