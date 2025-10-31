pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo '🔄 Cloning repository...'
                git branch: 'aeramana', url: 'https://github.com/ramanavundela/AutomationExercise.git'
            }
        }

        stage('Build Project') {
            steps {
                echo '🏗️ Building project with Maven...'
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Generate unique report name
                    def d = new Date()
                    env.REPORT_NAME = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html"

                    echo "🧾 Report will be generated as: ${env.REPORT_NAME}"

                    // Run tests and generate report
                    bat "mvn clean test -DreportName=${env.REPORT_NAME}"
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo '📊 Publishing Extent Report...'
                publishHTML([[
                    reportDir: 'extentReport',
                    reportFiles: '**/*.html',
                    reportName: 'Extent Report',
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ]])
            }
        }

        stage('Publish TestNG Report') {
            steps {
                echo '📈 Publishing TestNG HTML Report...'
                publishHTML([[
                    reportDir: 'test-output',
                    reportFiles: 'emailable-report.html',
                    reportName: 'TestNG Report',
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ]])
            }
        }
    }

    post {
        always {
            echo '📧 Sending report email...'
            emailext(
                subject: "📊 Automation Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                to: "ramanavundela@gmail.com",
                attachmentsPattern: """
                    extentReport/${env.REPORT_NAME},
                    test-output/emailable-report.html
                """,
                body: """
                    <h2>Automation Test Execution Summary</h2>
                    <p>Build #${env.BUILD_NUMBER} Status: <b>${currentBuild.currentResult}</b></p>
                    <ul>
                        <li><a href="${env.BUILD_URL}Extent_20Report/">📘 Extent Report</a></li>
                        <li><a href="${env.BUILD_URL}TestNG_20Report/">📄 TestNG Report</a></li>
                    </ul>
                    <p>Reports attached for offline view.</p>
                """,
                mimeType: 'text/html'
            )

            echo '🧹 Cleaning up workspace...'
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
