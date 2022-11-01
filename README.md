# air-tickets

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

**Backend stack:** Java SE, Spring Core, Spring Boot, Spring MVC, Spring Data, Spring Security,
Spring Cloud, Apache Tomcat, Jackson, Maven, JUnit, Mockito, MySQL, H2, Hibernate, Docker, Kubernetes.

## Build

### Build the backend

#### To run on Kubernetes
At first, make sure that [Docker](https://www.docker.com/) daemon is running.
Then, [Maven](https://maven.apache.org/) will build a Docker image for each microservice
and push it to the remote Docker repository.
```
mvn clean deploy
```

#### To run as a jar

[Maven](https://maven.apache.org/) will build a jar for each microservice.
```
mvn clean verify
```

## Run locally

#### On Kubernetes

You can spin up the Kubernetes deployment locally using [Minikube](https://minikube.sigs.k8s.io/docs/start/).
Give the `minikube_run` shell script the right to execute and run it.
```
chmod +x deployment/minikube_run.sh
./deployment/minikube_run.sh
```
Now you can access the application on http://localhost/

#### As a jar

```
mvn spring-boot:start -Dspring-boot.run.profiles=dev
```

Now you can access each microservice individually:

* User service - http://localhost:4545/
* Auth service - http://localhost:49937/
* Airline service - http://localhost:5973/

## Public API
All the endpoints follow the REST API Resource naming conventions.

#### Users:

* HTTP GET http://localhost/users?email=... - get the user registered with the specified email (autentication required)
* HTTP POST http://localhost/users - register a new user
* HTTP PATCH http://localhost/users?email=... - edit the user registered with the specified email (autentication required)
* HTTP DELETE http://localhost/users?email=... - delete the user registered with the specified email (autentication required)

#### Autentication:

* HTTP POST http://localhost/oauth/token - obtain an OAuth2 token

#### Airlines:

* HTTP GET http://localhost/airlines - get all airlines
* HTTP GET http://localhost/airlines/{id} - get the airline with the specified ID
* HTTP GET http://localhost/airlines?name=... - get the airline with the specified name
* HTTP POST http://localhost/airlines - create a new airline (autentication required)
* HTTP PATCH http://localhost/airlines/{id} - edit the airline with the specified ID (autentication required)
* HTTP DELETE http://localhost/airlines/{id} - delete the airline with the specified ID (autentication required)

#### Flights:

* HTTP GET http://localhost/flights - get all flights
* HTTP GET http://localhost/flights/{id} - get the flight with the specified ID
* HTTP GET http://localhost/flights?airline=... - get all flights with the specified airline ID
* HTTP POST http://localhost/flights - create a new flight (autentication required)
* HTTP PATCH http://localhost/flights/{id} - edit the flight with the specified ID (autentication required)
* HTTP DELETE http://localhost/flights/{id} - delete the flight with the specified ID (autentication required)

#### Tickets:

* HTTP GET http://localhost/tickets - get all tickets
* HTTP GET http://localhost/tickets/{id} - get the ticket with the specified ID
* HTTP GET http://localhost/tickets?flight... - get all tickets with the specified flight ID
* HTTP GET http://localhost/tickets?flight...&price=... - get all tickets with the specified flight ID
which price is lower that or equal to the specified price
* HTTP GET http://localhost/tickets?flight...&luggageAllowed=... - get all tickets with the specified flight ID
which allow luggage
* HTTP POST http://localhost/tickets - create a new ticket (autentication required)
* HTTP DELETE http://localhost/tickets/{id} - delete the ticket with the specified ID (autentication required)

## Screenshots
#### Jenkins build:

![jenkins](screenshots/jenkins.png)
