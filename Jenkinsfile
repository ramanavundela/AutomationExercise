pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        // 🧹 STEP 1: Clean workspace (remove any old reports or builds)
        stage('Clean Workspace') {
            steps {
                echo '🧹 Cleaning old workspace before starting new build...'
                bat '''
                if exist extentReport rmdir /S /Q extentReport
                if exist test-output rmdir /S /Q test-output
                if exist target rmdir /S /Q target
                '''
            }
        }

        // 🔄 STEP 2: Checkout Code from GitHub
        stage('Checkout Code') {
            steps {
                echo '🔄 Cloning repository from GitHub...'
                git branch: 'aeramana', url: 'https://github.com/ramanavundela/AutomationExercise.git'
            }
        }

        // 🏗️ STEP 3: Build the Maven Project
        stage('Build Project') {
            steps {
                echo '🏗️ Building project with Maven...'
                bat 'mvn clean compile -Dfile.encoding=UTF-8'
            }
        }

        // 🧪 STEP 4: Run Selenium + TestNG Tests
        stage('Run Tests') {
            steps {
                script {
                    def d = new Date()
                    env.REPORT_NAME = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html"
                    echo "🧾 Report will be generated as: ${env.REPORT_NAME}"

                    // Execute tests with unique report name passed via system property
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        bat "mvn clean test -Dfile.encoding=UTF-8 -DreportName=${env.REPORT_NAME}"
                    }
                }
            }
        }
    }

    // 📊 STEP 5: Post-build actions — collect, publish, email reports
    post {
        always {
            echo '📊 Collecting and publishing fresh reports...'

            // Ensure folders exist for safe copy
            bat '''
            if not exist extentReport mkdir extentReport
            if not exist test-output mkdir test-output
            '''

            // ✅ Find and copy the latest Extent Report (outside target)
            bat '''
            echo Searching for latest Extent report...
            set "foundExtent="
            for /f "delims=" %%f in ('dir /b /s /o-d extentReport\\Extent_*.html 2^>nul') do (
                echo Found Extent report: %%f
                copy /Y "%%f" "extentReport\\ExtentReport.html" >nul
                set "foundExtent=1"
                goto done
            )
            :done
            if not defined foundExtent (
                echo ⚠️ No ExtentReport found in extentReport folder!
            )
            exit /b 0
            '''

            // ✅ Find and copy the latest TestNG emailable-report.html
            bat '''
            echo Searching for TestNG report...
            set "foundTestNG="
            for /f "delims=" %%f in ('dir /b /s /o-d test-output\\emailable-report.html 2^>nul') do (
                echo Found TestNG report: %%f
                copy /Y "%%f" "test-output\\emailable-report.html" >nul
                set "foundTestNG=1"
                goto done
            )
            :done
            if not defined foundTestNG (
                echo ⚠️ No emailable-report.html found in test-output folder!
            )
            exit /b 0
            '''

            // ✅ Publish HTML reports in Jenkins UI
            publishHTML([
                reportDir: 'extentReport',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report (Latest)',
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])

            publishHTML([
                reportDir: 'test-output',
                reportFiles: 'emailable-report.html',
                reportName: 'TestNG Report (Latest)',
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true
            ])

            // ✅ Archive reports as a downloadable ZIP
            echo '📦 Creating reports.zip for download...'
            bat '''
            powershell -Command "Compress-Archive -Path extentReport\\ExtentReport.html, test-output\\emailable-report.html -DestinationPath reports.zip -Force"
            '''
            archiveArtifacts artifacts: 'reports.zip', fingerprint: true

            // ✅ Send Email with latest reports
            echo '📧 Sending email with latest reports...'
            emailext(
                subject: "📊 Automation Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                to: "ramanavundela@gmail.com",
                attachmentsPattern: """
                    extentReport/ExtentReport.html,
                    test-output/emailable-report.html,
                    reports.zip
                """,
                body: """
                    <h2>Automation Test Summary</h2>
                    <p>Build #${env.BUILD_NUMBER} - Status: <b>${currentBuild.currentResult}</b></p>
                    <ul>
                        <li><a href="${env.BUILD_URL}Extent_20Report_(Latest)/">📘 View Extent Report</a></li>
                        <li><a href="${env.BUILD_URL}TestNG_20Report_(Latest)/">📄 View TestNG Report</a></li>
                        <li><a href="${env.BUILD_URL}artifact/reports.zip">📦 Download All Reports (ZIP)</a></li>
                    </ul>
                    <p>Reports are attached for offline viewing.</p>
                """,
                mimeType: 'text/html'
            )

            // Optional: Clean workspace after publishing
            echo '🧽 Cleaning workspace after publishing...'
            cleanWs()
        }

        success {
            echo '✅ Build Successful — Reports published successfully.'
        }

        unstable {
            echo '⚠️ Tests failed — Reports still published.'
        }

        failure {
            echo '❌ Build Failed — Reports still generated.'
        }
    }
}
