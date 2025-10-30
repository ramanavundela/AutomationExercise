pipeline {
    agent any  // runs on any available Jenkins agent

    tools {
        maven 'Maven'     // Maven tool name configured in Jenkins
        jdk 'JDK17'       // JDK tool name configured in Jenkins
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Cloning repository...'
                git branch: 'aeramana', url: 'https://github.com/ramanavundela/AutomationExercise.git'
            }
        }

        stage('Build Project') {
            steps {
                echo 'Building project with Maven...'
                bat 'mvn clean compile'
            }
        }

        stage('Run Test Cases') {
            steps {
                echo 'Executing Selenium + TestNG tests...'
                bat 'mvn test'
            }
        }

        stage('Generate Reports') {
            steps {
                echo 'Publishing Extent/TestNG report...'
                publishHTML(target: [
                    reportDir: 'test-output',
                    reportFiles: 'emailable-report.html',
                    reportName: 'TestNG HTML Report'
                ])
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo '✅ Build Successful!'
        }
        failure {
            echo '❌ Build Failed!'
        }
    }
}
