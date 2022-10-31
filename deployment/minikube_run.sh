#!/bin/bash
# Author : Alexander Tolpeko

echo "Deleting existing minikube machine"
minikube delete

echo "Creating new minikube machine"
minikube start
minikube addons enable ingress

echo "Spinning up kubernetes cluster"

# Ingress
kubectl create -f ingress-deployment.yaml

# MySQL
kubectl create -f mysql/mysql-storage.yaml
kubectl create -f mysql/mysql-secret.yaml
kubectl create -f mysql/mysql-deployment.yaml

# User service
kubectl create -f user-service/user-service-secret.yaml
kubectl create -f user-service/user-service-deployment.yaml

# Auth service
kubectl create -f auth-service/auth-service-secret.yaml
kubectl create -f auth-service/auth-service-deployment.yaml 

echo "Running tunnel. You can access the application on http://localhost/"
minikube tunnel
