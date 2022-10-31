# cmarket

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Build

### Build the backend

Then, [Maven](https://maven.apache.org/) will build a Docker image for each microservice
and push it to the remote Docker repository.
```
mvn clean deploy
```
> Note: If the deployment fails, run `mvn clean install` and manually push all built images.

## Run locally

You can spin up the Kubernetes deployment locally using [Minikube](https://minikube.sigs.k8s.io/docs/start/).
Give the `minikube_run` shell script the right to execute and run it.
```
chmod +x deployment/minikube_run.sh
./deployment/minikube_run.sh
```
Now you can access the application on http://localhost/
