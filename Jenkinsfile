
pipeline {
    agent any

    tools {
        maven 'Maven-3.10'
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Tests') {
            steps {
                bat 'mvn -B test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.war',
                             fingerprint: true
        }
    }
}