
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

stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQubeCloud') {
            bat '''
                mvn -B org.sonarsource.scanner.maven:sonar-maven-plugin:sonar ^
-Dsonar.organization=harrathidhekra ^
-Dsonar.projectKey=harrathidhekra_GestionEtudiants
            '''
        }
    }
}


        stage('Package') {
            steps {
                bat 'mvn -B package'
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                if not exist "C:\\Tomcat\\webapps" exit /b 1
                copy /Y "target\\GestionEtudiants.war" "C:\\Tomcat\\webapps\\GestionEtudiants.war"
                '''
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
