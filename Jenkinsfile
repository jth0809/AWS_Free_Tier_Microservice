pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker_hub_account'
        DOCKER_HUB_REPO = 'jdh0809'
    }

    stages {
        stage('Install Docker CLI') {
            steps {
                script {
                    // Install Docker CLI if not already installed
                    sh 'apt-get update && apt-get install -y docker.io'
                }
            }
        }
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def projects = ['budget', 'edge', 'location', 'member', 'nginx', 'orchestration', 'post']

                    projects.each { project ->
                        dir(project) {
                            try {
                                sh 'chmod +x ./gradlew'
                                sh './gradlew clean build --no-daemon'

                                def dockerfilePath = "dockerfile.${project}"
                                def imageName = "${DOCKER_HUB_REPO}/easytrip:${project}"

                                // Docker 빌드
                                docker.build(imageName, "-f ${dockerfilePath} .")

                                // Docker Hub에 로그인
                                withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                                        docker.image(imageName).push()
                                    }
                                }

                                echo "Successfully pushed ${imageName}"
                            } catch (Exception e) {
                                echo "Error processing ${project}: ${e.message}"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker system prune -af'
        }
        success {
            echo 'All projects have been built and pushed successfully.'
        }
        failure {
            echo 'One or more projects failed to build or push.'
        }
    }
}