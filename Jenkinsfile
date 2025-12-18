pipeline {
    agent any
    environment {
        IMAGE_NAME      = "jayesh1410/spring-backend"
        DOCKERHUB_USER  = "jayesh1410"
        IMAGE_TAG = "build-${BUILD_NUMBER}"
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages {

        /* ------------------------ CLONE REPO ------------------------ */

        stage('Clone Repo') {
            steps {
                sshagent(['git-creds']) {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[
                            url: 'git@github.com:jayesh6442/spring-boot-k8s-deploy.git',
                            credentialsId: 'git-creds'
                        ]]
                    ])
                }
            }
        }

        /* ------------------------ DEBUG WORKSPACE ------------------------ */

        stage('Debug Workspace') {
            steps {
                sh '''
                    echo ">>> Debug Workspace"
                    pwd
                    ls -al
                '''
            }
        }


        stage('Docker Build') {
            steps {
                sh '''
                    echo ">>> Building Docker image..."
                    docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                '''
            }
        }


        /* ------------------------ PUSH TO DOCKER HUB ------------------------ */

        stage('Docker Hub Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo ">>> Pushing image to Docker Hub..."

                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin


                        docker push ${IMAGE_NAME}:${IMAGE_TAG}

                        docker logout
                    '''
                }
            }
        }


    /* ------------------------ EMAIL NOTIFICATIONS ------------------------ */

    post {
        success {
            emailext(
                to: "jayeshsarvaiya9913@gmail.com",
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Hello Jayesh,

Your Jenkins pipeline SUCCEEDED!

Build Number: ${env.BUILD_NUMBER}
URL: ${env.BUILD_URL}

Regards,
Jenkins
"""
            )
        }

        failure {
            emailext(
                to: "jayeshsarvaiya9913@gmail.com",
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Hello Jayesh,

Your Jenkins pipeline FAILED ❌

Build Number: ${env.BUILD_NUMBER}
URL: ${env.BUILD_URL}

Regards,
Jenkins
"""
            )
        }
    }
}
}