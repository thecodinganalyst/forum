pipeline {
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
    }
}