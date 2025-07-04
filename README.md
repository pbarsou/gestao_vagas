# Job Management

## About the Project

This project involves the development of a **REST API** that allows companies to **create and manage their job openings**. Meanwhile, potential candidates can **register**, **view available jobs**, **apply for positions**, and access relevant information about their **profile** and **applied jobs**.

## Technologies Used

* **Programming Language:** Java 17
* **Framework:** Spring (Spring Initializr)
* **Dependency Management:** Maven
* **Database:** PostgreSQL, HSQLDB (for Tests)
* **Cloud:** AWS, Azure
* **Additional Tools:** Hibernate (JPA), Lombok, Docker, Kubernetes, SonarQube, JaCoCo, Prometheus, and Grafana

## Running the Project

1.  `$ docker-compose -build -d`
    * Container configurations are defined in the `docker-compose.yml` file.
    * Once the container is built, you don't need to rebuild it: `$ docker-compose up -d`.

## Features

### For Companies:

* **Create New Job Openings:** Companies can register new job openings, providing details like description, benefits, and experience level.
* **View Registered Job Openings:** Companies can view detailed information about a specific job opening, including the list of registered candidates.
* **View and Update Profile:** Companies can view their registration profile, including their registered data (name, username, email, website, and description) and a list of jobs they've created. Companies can also update their profile data.

### For Candidates:

* **Apply for a Job:** Candidates can apply for one or more available job openings.
* **View Profile:** Candidates can view their registration profile, including their registered data (name, username, email, description) and the list of jobs they've applied for. Candidates can also update their profile data.

## Business Rules

* A candidate can apply for a job only once.
* It should not be possible to create a new candidate with an existing username or email.
* It should not be possible to create a company with an existing username or email.

## Project Structure

* **Controllers:** Responsible for receiving and processing HTTP requests.
* **Services:** Contain the application's business logic.
* **Repositories:** Responsible for interacting with the database.
* **DTOs (Data Transfer Objects):** Used for transferring data between the client and the server.
* **Models:** Represent the database entities.
* **Exception Handling:** Custom exception handling to properly manage errors.
* **Security:** Application security module.

## Documentation

The application's documentation was created using **Swagger**, one of the most widely used tools for documentation generation.

* Documentation access route: `http://localhost:8080/swagger-ui/index.html`

## Unit and Integration Tests

For testing, **JUnit** was used in conjunction with **Mockito** for creating mocks.

**H2**, an in-memory database, was utilized for testing purposes.

## Code Quality

#### SonarQube + JaCoCo

For code quality, **SonarQube** was employed. It's a code analysis tool that helps detect potential issues in the code. It was used alongside **JaCoCo**, a code coverage analysis tool that helps measure how much of the Java source code has been tested.

* Available at the endpoint: `localhost:9000`. Register manually to generate an access key. The key has an expiration date.

Example SonarQube login configuration:

```shell:
mvn clean verify sonar:sonar -Dsonar.projectKey=gestao_vagas -Dsonar.host.url=http://localhost:9000 -Dsonar.login=access_key
```

## Monitoring

We use **Spring Actuator** for application monitoring, along with **Prometheus** and **Grafana** for visual representation of application metrics.

#### Relevant Information

* Access application metrics via Actuator:

    ```json
    {
      "_links": {
        "self": {
          "href": "http://localhost:8080/actuator",
          "templated": false
        },
        "health-path": {
          "href": "http://localhost:8080/actuator/health/{*path}",
          "templated": true
        },
        "health": {
          "href": "http://localhost:8080/actuator/health",
          "templated": false
        },
        "prometheus": {
          "href": "http://localhost:8080/actuator/prometheus",
          "templated": false
        },
        "metrics": {
          "href": "http://localhost:8080/actuator/metrics",
          "templated": false
        },
        "metrics-requiredMetricName": {
          "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
          "templated": true
        }
      }
    }
    ```

* Access Prometheus: `localhost:9090`
* Access Grafana: `localhost:3000`

## Deployment

Initially, Docker was used with Azure Kubernetes Service + Azure Container Registry. However, due to the expiration of the free tier, I migrated the application to **AWS**. I created an **RDS instance with PostgreSQL** as the database and an **EC2 instance**. On the EC2 instance, I configured a **CI/CD pipeline** for database connection and deployment to the EC2 instance with each `git push`.

## Key Endpoints

**Open Endpoints:**

* **POST /candidate:** Creates a new candidate.
* **POST /company:** Creates a new company.
* **GET /candidate/auth:** Creates an access token for a candidate.
* **GET /company/auth:** Creates an access token for a company.
* **GET /candidate/auth/admin:** Creates an access token for a candidate in administrator mode.
* **GET /company/auth/admin:** Creates an access token for a company in administrator mode.

**Endpoints Requiring Authorization:**

* **POST /candidate/job/apply:** Allows a candidate to apply for a job.
* **POST /company/job/:** Creates a new job opening.

### Other Endpoints Requiring Authentication

### Main Libraries (Packages)

* [`Spring Boot`](https://spring.io/)
* [`Project Lombok`](https://projectlombok.org/)
* [`Docker`](https://www.docker.com/)
* [`PostgreSQL`](https://www.postgresql.org/)
* [`java-jwt`](https://github.com/auth0/java-jwt)
* [`Swagger`](https://swagger.io/)
* [`JUnit`](https://junit.org/junit4/)
* [`H2 Database`](https://www.h2database.com/html/main.html)

---

<h5 align="center">
  2024 - <a href="https://github.com/pbarsou/">Pablo Barbosa</a>
</h5>
