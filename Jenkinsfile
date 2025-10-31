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
                bat 'mvn clean compile -Dfile.encoding=UTF-8'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Generate unique report name
                    def d = new Date()
                    env.REPORT_NAME = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html"
                    echo "🧾 Report will be generated as: ${env.REPORT_NAME}"

                    // Run tests and continue even if failures occur
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        bat "mvn clean test -Dfile.encoding=UTF-8 -DreportName=${env.REPORT_NAME}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo '📊 Publishing Extent & TestNG Reports...'

            // ✅ Use SINGLE map — not list — for Jenkins 2.531+
            publishHTML([
                reportDir: 'extentReport',
                reportFiles: '**/*.html',
                reportName: 'Extent Report',
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])

            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'emailable-report.html',
                reportName: 'TestNG Report',
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])

            // ✅ Send email with reports
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
                    <p>Reports are attached for offline view.</p>
                """,
                mimeType: 'text/html'
            )

            echo '🧹 Cleaning up workspace...'
            cleanWs()
        }

        success {
            echo '✅ Build Successful!'
        }

        unstable {
            echo '⚠️ Some tests failed — Reports still generated.'
        }

        failure {
            echo '❌ Build Failed — Reports still generated.'
        }
    }
}
