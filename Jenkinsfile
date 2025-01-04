pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker_hub_account' // Jenkins에 저장된 Docker Hub 자격 증명 ID
        DOCKER_HUB_REPO = 'jdh0809' // Docker Hub 사용자 이름
    }

    stages {
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def projects = ['budget', 'edge', 'location', 'member', 'nginx', 'orchestration', 'post']

                    projects.each { project -> 
                        dir(project) {
                            try {
                                sh 'chmod +x ./gradlew'
                                // Gradle 빌드 실행
                                sh './gradlew clean build --no-daemon'

                                // Docker 이미지 생성 및 푸시
                                def dockerfilePath = "dockerfile.${project}"
                                def imageName = "${DOCKER_HUB_REPO}/easytrip:${project}"
                                
                                // Docker 빌드
                                docker.build(imageName, "-f ${dockerfilePath} .")

                                // Docker Hub에 로그인 및 푸시
                                docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                                    docker.image(imageName).push()
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
            // Docker 이미지 캐시 정리
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