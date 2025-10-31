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
                    def d = new Date()
                    env.REPORT_NAME = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html"
                    echo "🧾 Report will be generated as: ${env.REPORT_NAME}"

                    // Continue even if tests fail
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        bat "mvn clean test -Dfile.encoding=UTF-8 -DreportName=${env.REPORT_NAME}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo '📊 Preparing reports for publishing and archiving...'

            // Ensure report folders exist
            bat '''
            if not exist extentReport mkdir extentReport
            if not exist test-output mkdir test-output
            '''

            // Copy the latest Extent report to a fixed name
            bat """
            for /r extentReport %%f in (*.html) do copy /Y "%%f" "extentReport\\ExtentReport.html"
            """

            // ✅ Publish Extent report (always latest)
            publishHTML([
                reportDir: 'extentReport',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report (Latest)',
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])

            // ✅ Publish TestNG report
            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'emailable-report.html',
                reportName: 'TestNG Report',
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])

            // ✅ Zip both reports together for archive/download
            echo '📦 Creating reports.zip...'
            bat '''
            powershell -Command "Compress-Archive -Path extentReport\\ExtentReport.html, test-output\\emailable-report.html -DestinationPath reports.zip -Force"
            '''

            // ✅ Archive zip as Jenkins artifact
            archiveArtifacts artifacts: 'reports.zip', fingerprint: true

            // ✅ Send email with both reports
            echo '📧 Sending report email...'
            emailext(
                subject: "📊 Automation Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                to: "ramanavundela@gmail.com",
                attachmentsPattern: """
                    extentReport/ExtentReport.html,
                    test-output/emailable-report.html,
                    reports.zip
                """,
                body: """
                    <h2>Automation Test Execution Summary</h2>
                    <p>Build #${env.BUILD_NUMBER} Status: <b>${currentBuild.currentResult}</b></p>
                    <ul>
                        <li><a href="${env.BUILD_URL}Extent_20Report_(Latest)/">📘 View Extent Report</a></li>
                        <li><a href="${env.BUILD_URL}TestNG_20Report/">📄 View TestNG Report</a></li>
                        <li><a href="${env.BUILD_URL}artifact/reports.zip">📦 Download All Reports (ZIP)</a></li>
                    </ul>
                    <p>Reports attached and archived for offline view.</p>
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
