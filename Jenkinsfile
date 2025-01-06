pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker_hub_account'
        DOCKER_HUB_REPO = 'jdh0809'
    }

    stages {
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def projects = ['budget', 'edge', 'location', 'member', 'nginx', 'orchestration', 'post']

                    projects.each { project ->
                        dir(project) {
                            try {
                                sh "ls ../ -al"
                                sh 'chmod +x ./gradlew'
                                sh './gradlew clean build --no-daemon'

                                def dockerfilePath = "../dockerfile.${project}"
                                def imageName = "${DOCKER_HUB_REPO}/easytrip:${project}"

                                container('dind-daemon') {
                                    sh "cd .."
                                    sh "ls ./build/libs/ -al"
                                    docker.build(imageName, "-f ${dockerfilePath} ..")
                                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                        docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                                            docker.image(imageName).push()
                                    }
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
}