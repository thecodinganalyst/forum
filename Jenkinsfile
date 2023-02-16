pipeline {
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
    }
    agent { docker { image 'gradle:7.6.0-jdk17-alpine' } }
    stages {
        stage('build') {
            steps {
                sh 'gradle build'
            }
        }
        stage('test'){
            steps {
                sh 'gradle test'
            }
        }
        stage("quality review") {
            steps {
                withSonarQubeEnv('Sonarqube'){
                    sh "gradle sonarqube"
                }
            }
            post {
                success {
                    echo "SonarQube analysis passed"
                }
                failure {
                    echo "SonarQube analysis failed"
                    exit 1
                }
            }
        }
        stage('deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
                    sh 'docker build -t forum:latest .'
                    sh 'docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD'
                    sh 'docker push forum:latest'
                }
            }
        }
    }
}