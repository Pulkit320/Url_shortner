# URL Shortener

A RESTful URL Shortener built with Java, Spring Boot, Spring Data JPA, PostgreSQL, and Flyway. The application generates short codes for long URLs, persists mappings in PostgreSQL, and redirects users to their original URLs.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **Persistence:** Spring Data JPA / Hibernate
- **Database migrations:** Flyway
- **Build tool:** Maven
- **Testing:** JUnit 5, Spring Boot Test, MockMvc
- **CI:** GitHub Actions
- **Local database environment:** Docker Compose

## Features

- Create short URLs through a REST API.
- Generate six-character Base62 short codes.
- Persist URL mappings in PostgreSQL.
- Redirect short URLs using HTTP 302.
- Validate incoming requests.
- Return structured error responses.
- Handle short-code uniqueness and collisions.
- Manage database schema changes with Flyway.
- Run automated tests through Maven and GitHub Actions.

## Prerequisites

Install the following:

- Java 21
- Docker Engine with Docker Compose
- Git

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Pulkit320/Url_shortner.git
cd Url_shortner
```

### 2. Configure environment variables

The application reads database credentials from environment variables or a local `.env` file.

Create `.env` in the project root:

```properties
DB_USERNAME=pulkit
DB_PASSWORD=your_local_password
```

Use credentials appropriate for your local PostgreSQL setup. Never commit `.env` or share real credentials.

### 3. Start PostgreSQL with Docker Compose

Create `.env.docker` in the project root:

```properties
POSTGRES_PASSWORD=your_local_password
```

Use a secure local password of your choice. Keep this file out of Git.

Start the database:

```bash
docker compose --env-file .env.docker up -d
```

Check its status:

```bash
docker compose --env-file .env.docker ps
```

The Compose configuration exposes PostgreSQL on host port `5333` and creates the `urlshortner` database. A named volume persists the database data.

### 4. Run the application

To use the Compose-managed database on port `5333`, run:

```bash
DB_URL=jdbc:postgresql://localhost:5333/urlshortner ./mvnw spring-boot:run
```

The application uses Flyway to manage database migrations and Hibernate schema validation to check the database schema.

By default, the application connects to `localhost:5332/urlshortner`, which supports an existing local PostgreSQL setup. Set `DB_URL` when you want to override that default.

## Running Tests

Run the full test suite:

```bash
./mvnw clean test
```

The test profile uses the `urlshortner_test` database on port `5332`. Ensure that this database is available and that `DB_USERNAME` and `DB_PASSWORD` are configured for your test environment.

Tests cover application startup, service logic, controller behavior, and HTTP integration using MockMvc.

## Continuous Integration

GitHub Actions runs the Maven test suite on pushes and pull requests targeting `master` or `main`.

The workflow starts a PostgreSQL service, configures Java 21, and executes:

```bash
./mvnw clean test
```

See `.github/workflows/ci.yml` for the CI configuration.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/urls` | Create a short URL |
| `GET` | `/{shortCode}` | Redirect to the original URL |

### Create a short URL

Example request:

```http
POST /api/urls
Content-Type: application/json
```

```json
{
  "originalUrl": "https://example.com"
}
```

The response contains the generated short-code information. Refer to the application's DTOs for the exact response structure.

### Redirect to the original URL

Send a GET request to the generated short URL:

```http
GET /{shortCode}
```

A valid short code returns an HTTP `302 Found` redirect. An unknown short code returns `404 Not Found`.

## Database Migrations

Flyway migrations are stored under:

```text
src/main/resources/db/migration/
```

The initial migration creates the `url_mappings` table. New schema changes should be introduced through additional versioned migrations.

Hibernate uses `ddl-auto=validate` so that the application checks the schema rather than automatically modifying it.

## Project Structure

```text
.
├── .github/
│   └── workflows/
│       └── ci.yml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── db/migration/
│   └── test/
├── compose.yaml
├── pom.xml
├── mvnw
└── README.md
```

## Stopping the Compose Database

Stop the database container without deleting its persistent data:

```bash
docker compose --env-file .env.docker down
```

The named volume is retained. Do not use `docker compose down -v` unless you intentionally want to delete the Compose-managed database volume.

## License

No license has been specified yet.