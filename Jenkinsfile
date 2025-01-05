pipeline {
    agent {
        docker {
            image 'maven:3.8.1-openjdk-17'  // Maven과 Java 17 환경을 사용하는 이미지로 변경
            args '-v /var/run/docker.sock:/var/run/docker.sock'  // Docker 소켓을 공유
        }
    }

    environment {
        DOCKER_CREDENTIALS_ID = 'docker_hub_account'
        DOCKER_HUB_REPO = 'jdh0809'
        DOCKER_HOST = 'tcp://172.24.208.1:2375'
    }

    stages {
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def projects = ['budget', 'edge', 'location', 'member', 'nginx', 'orchestration', 'post']

                    projects.each { project -> 
                        dir(project) {
                            try {
                                // Gradle 빌드 실행
                                sh 'chmod +x ./gradlew'
                                sh './gradlew clean build --no-daemon'

                                def dockerfilePath = "dockerfile.${project}"
                                def imageName = "${DOCKER_HUB_REPO}/easytrip:${project}"

                                // Docker 빌드
                                docker.build(imageName, "-f ${dockerfilePath} .")

                                // Docker Hub에 로그인 후 이미지 푸시
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