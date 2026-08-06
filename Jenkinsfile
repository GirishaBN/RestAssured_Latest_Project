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
                checkout scm
            }
        }

        stage('Run API Tests') {
            steps {
				withCredentials([
                    string(
                        credentialsId: 'bearer-token',
                        variable: 'BEARER_TOKEN'
                    )
                ])
				{
                bat "mvn clean test -Denv=${ENV} -Dgroups=${GROUPS}"
                }
            }
        }
    }
}