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
			 emailext(
                subject: "Automation Test Report - Build #${env.BUILD_NUMBER}",
                body: """
                    <p>Hi Team,</p>
                    <p>The automation suite has completed. Please find the report below:</p>
                    <p><a href="${env.BUILD_URL}HTML_20Report/">Click here to view report</a></p>
                    <p>Thanks,<br>Automation Jenkins</p>
                """,
                mimeType: 'text/html',
                attachLog: true,
                attachmentsPattern: 'test-output/emailable-report.html',
                to: 'ramanavundela@gmail.com'
            )
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
