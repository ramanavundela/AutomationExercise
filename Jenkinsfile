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
