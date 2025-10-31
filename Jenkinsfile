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
        stage('Run Tests') {
        steps {
        script {
            // generate timestamp for report
            def d = new Date();
            env.REPORT_NAME = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

            echo "🧾 Report will be generated as: ${env.REPORT_NAME}"

            // run Maven with report name as system property
            bat "mvn clean test -DreportName=${env.REPORT_NAME}"
        }
        }
        }
        stage('Publish Extent Report') {
         steps {
           echo 'Publishing Extent Report...'
           publishHTML([
            reportDir: 'test-output/ExtentReports',
            reportFiles: 'ExtentReport.html',
            reportName: 'Extent Report',
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true
        ])
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
            subject: "📊 Automation Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
            to: "ramanavundela@gmail.com",
            attachmentsPattern: """
                test-output/extentReport/${env.REPORT_NAME},
                test-output/emailable-report.html
            """,
            body: """
                <h2>Automation Test Execution Summary</h2>
                <p>Hi Team,</p>
                <p>The Jenkins build <b>#${env.BUILD_NUMBER}</b> has completed with status:
                <b>${currentBuild.currentResult}</b>.</p>

                <p><b>Click below to view reports:</b></p>
                <ul>
                    <li><a href="${env.BUILD_URL}Extent_20Report/">📘 ${env.REPORT_NAME}</a></li>
                    <li><a href="${env.BUILD_URL}TestNG_20Emailable_20Report/">📄 Emailable Report</a></li>
                </ul>

                <p>Both reports are also attached to this email for offline viewing.</p>
                <p>Thanks,<br><b>Jenkins Automation</b></p>
            """,
            mimeType: 'text/html'
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
