# Vanilla Backend Project

This is a simple template I use to quickly start a Spring Boot project.

## Technical Summary for AI

**Project**: Spring Boot Kotlin backend skeleton/template
**Package**: `de.codecentric.vanilla`
**Version**: 0.0.1-SNAPSHOT

## Technology Stack
- **Language**: Kotlin 2.0.10
- **Framework**: Spring Boot 3.5.4
- **Build Tool**: Gradle with Kotlin DSL
- **JVM**: Java 21
- **Static Analysis**: Detekt
- **Container**: GCP Artifact Registry ready with Cloud Native Buildpacks

## Key Dependencies
- Spring Boot Web Starter
- Jackson Kotlin Module
- Kotlin Logging (OShai)
- Logstash Logback Encoder

## Testing Stack
- Spring Boot Test
- JUnit 5
- Kotest Assertions
- MockK
- SpringMockK

## Project Structure
```
src/main/kotlin/de/codecentric/vanilla/
├── VanillaApplication.kt - Main entry point with Spring Boot configuration
├── age/
│   ├── AgeController.kt - REST endpoint for age calculation
│   └── AgeCalculator.kt - Service for age calculation logic
└── properties/
    └── ApplicationProperties.kt - Configuration properties binding
```

## Features
- REST API endpoint `/api/age` that calculates age from birthdate
- Configuration properties support via `ApplicationProperties`
- Structured logging with Logstash encoder
- Docker image build support for GCP Artifact Registry
- Detekt code quality checks with auto-correction
- Test configuration with separate application-test.yaml

## Configuration
- Application name: `vanilla`
- Spring banner disabled
- Custom greeting property configurable
- JVM optimized for containerization (400MB heap, 128MB metaspace)

This is a clean, production-ready Spring Boot Kotlin template suitable for microservices development with GCP deployment capabilities.