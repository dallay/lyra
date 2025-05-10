# Lyra Project Development Guidelines

This document provides essential information for developers working on the Lyra project.

## Build and Configuration Instructions

### Prerequisites
- Java (version specified in `.java-version`)
- Node.js (version specified in `.nvmrc`)
- PNPM (for frontend package management)
- Docker and Docker Compose (for running dependencies)

### Building the Project

#### Backend (Kotlin/Spring Boot)
The project uses Gradle as the build tool. The Gradle wrapper (`gradlew`) is included in the repository, so you don't need to install Gradle separately.

```bash
# Build the entire project
./gradlew build

# Clean and build
./gradlew clean build

# Build specific module
./gradlew :apps:backend:build
```

You can also use the Makefile for common operations:
```bash
# Build the project
make build

# Clean the project
make clean

# Run tests
make test
```

#### Frontend (Node.js)
The frontend uses PNPM for package management:

```bash
# Install dependencies
pnpm install

# Build frontend
pnpm run build
```

### Running the Application

#### Using Docker Compose
The project includes Docker Compose configuration for running the application and its dependencies:

```bash
# Start all services
docker compose up

# Start specific services
docker compose up postgresql keycloak
```

#### Environment Configuration
The project uses `.env` files for environment configuration. Copy `.env.example` to `.env` and adjust the values as needed:

```bash
cp .env.example .env
```

## Testing Information

### Backend Testing

The backend uses JUnit 5 for testing, with custom annotations for different test types:

- `@UnitTest`: For unit tests
- `@IntegrationTest`: For integration tests

Tests follow a BDD style with Given/When/Then comments and are organized by domain and layer (application, infrastructure, domain).

#### Running Backend Tests

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests "com.lyra.app.healthcheck.HealthcheckUtilTest"
```

#### Writing Backend Tests

1. Create a test class in the appropriate package under `src/test/kotlin`
2. Annotate the class with `@UnitTest` or `@IntegrationTest`
3. Write test methods with descriptive names using backticks
4. Follow the Given/When/Then pattern

Example:
```kotlin
@UnitTest
class HealthcheckUtilTest {

    @Test
    fun `should return true when system is healthy`() {
        // Given
        val healthcheckUtil = HealthcheckUtil()

        // When
        val result = healthcheckUtil.isHealthy()

        // Then
        Assertions.assertTrue(result)
    }
}
```

### Frontend Testing

Frontend tests are written using the Vitest JavaScript testing framework and are located in the `apps/frontend` directory.

#### Running Frontend Tests

```bash
# Run all frontend tests
pnpm test

# Run tests with watch mode
pnpm test:watch
```

#### Writing Frontend Tests

Frontend tests follow a similar BDD style with describe/it blocks:

```typescript
describe('StringValueObject', () => {
  it('should create a StringValueObject with a valid value', () => {
    const valueObject = TestValueObject.create('test');
    expect(valueObject.value).toBe('test');
  });
});
```

## Code Style and Development Practices

### Code Style

The project uses EditorConfig for consistent code style across different editors. The main settings are:

- UTF-8 encoding
- LF line endings
- 2-space indentation for most files
- 4-space indentation for Kotlin, Java, and some other file types

### Architecture

The project follows a clean architecture pattern with:

- Domain layer: Core business logic and entities
- Application layer: Use cases and application services
- Infrastructure layer: External interfaces and implementations

### Backend Development

- The backend is built with Kotlin and Spring Boot
- Uses reactive programming with Spring WebFlux
- Follows CQRS pattern with command and query handlers
- Uses event-driven architecture with event publishers

### Frontend Development

- The frontend consists of multiple applications:
  - Lyra App (Nuxt.js)
  - Lyra Landing Page (Astro)
- Uses TypeScript for type safety
- Follows domain-driven design principles

### Database

- PostgreSQL is used as the primary database
- Keycloak is used for authentication and authorization

### Containerization

- Docker is used for containerization
- Docker Compose is used for local development
- Multiple Dockerfiles for different environments (development, production)

## Debugging and Troubleshooting

### Logs

- Backend logs are available in the console and can be configured in `application.yml`
- Docker logs can be viewed with `docker compose logs`

### Common Issues

- If you encounter database connection issues, ensure PostgreSQL is running and accessible
- For Keycloak issues, check the Keycloak logs and ensure it's properly configured

## Continuous Integration

The project uses GitHub Actions for CI/CD, with workflows defined in the `.github` directory.
