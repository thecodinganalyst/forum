pipeline {
    agent { docker { image 'gradle:7.6.0-jdk17-alpine' } }
    stages {
        stage('build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('test'){
            steps {
                sh './gradlew test'
            }
        }
    }
}