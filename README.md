# air-tickets

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

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
