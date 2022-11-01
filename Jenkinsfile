pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        dir("frontend/") {
                            sh "npm install"
                            sh "npm run build"
                            sh "docker build -t airtickets/frontend:1.0 ."
                        }
                        sh "mvn clean package -Dmaven.test.skip=true"
                        sh "mvn dockerfile:build"
                    } else {
                        dir('frontend/') {
                            bat "npm install"
                            bat "npm run build"
                            bat "docker build -t airtickets/frontend:1.0 ."
                        }
                        bat "mvn clean package \"-Dmaven.test.skip=true\""
                        bat "mvn dockerfile:build"
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh "mvn test-compile"
                        sh "mvn surefire:test"
                        sh "mvn failsafe:integration-test"
                    } else {
                        bat "mvn test-compile"
                        bat "mvn surefire:test"
                        bat "mvn failsafe:integration-test"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
