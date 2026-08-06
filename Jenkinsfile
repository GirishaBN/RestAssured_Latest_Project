pipeline {

    agent any

    parameters {
        choice(
            name: 'ENV',
            choices: ['qa', 'stage', 'prod'],
            description: 'Environment to execute tests'
        )

        choice(
            name: 'GROUPS',
            choices: ['smoke', 'regression'],
            description: 'TestNG group to execute'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/GirishaBN/RestAssured_Latest_Project.git'
            }
        }

        stage('Run API Tests') {
            steps {
                bat "mvn clean test -Denv=${ENV} -Dgroups=${GROUPS}"
            }
        }
    }
}